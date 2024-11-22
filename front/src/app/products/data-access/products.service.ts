import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Product } from './product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  private readonly http = inject(HttpClient);
  private readonly path = "/api/products";
  
  private readonly _products = new BehaviorSubject<Product[]>([]);
  public readonly products = this._products.asObservable();

  constructor() {
    this.get().subscribe();
  }

  public get(): Observable<Product[]> {
    return this.http.get<Product[]>(this.path).pipe(
      catchError((error) => {
        return this.http.get<Product[]>("assets/products.json");
      }),
      tap((products) => this._products.next(products))
    );
  }

  public update(product: Product): Observable<Product> {
    return this.http.put<Product>(`${this.path}/${product.id}`, product).pipe(
      tap(() => {
        const products = this._products.value.map(p => p.id === product.id ? product : p);
        this._products.next(products);
      })
    );
  }

  public create(product: Product): Observable<Product> {
    return this.http.post<Product>(this.path, product).pipe(
      tap((newProduct) => {
        this._products.next([...this._products.value, newProduct]);
      })
    );
  }

  public delete(productId: number): Observable<void> {
    return this.http.delete<void>(`${this.path}/${productId}`).pipe(
      tap(() => {
        const products = this._products.value.filter(p => p.id !== productId);
        this._products.next(products);
      })
    );
  }
}