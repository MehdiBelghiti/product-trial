import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { CartItem } from './cart.model';
import { ProductsService } from 'app/products/data-access/products.service';
import { Product } from 'app/products/data-access/product.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private readonly http = inject(HttpClient);
  private readonly productService = inject(ProductsService);
  private readonly path = "/api/cart";
  
  private readonly _cartItems = new BehaviorSubject<CartItem[]>([]);
  private readonly _products = new BehaviorSubject<Product[]>([]);

  public readonly cartItems = this._cartItems.asObservable();
  public readonly products = this._products.asObservable();

  constructor() {
    this.productService.get().subscribe(products => {
      this._products.next(products);
    });
  }

  public get(): Observable<CartItem[]> {
    return this.http.get<CartItem[]>(this.path).pipe(
      catchError((error) => {
        return of(this._products.value.filter((product: Product) => product.quantity && product.quantity > 0).map((product: Product) => {
            return {
              product,
              quantity: product.quantity,
            } as CartItem;
          }));
      }),
      tap((items) => this._cartItems.next(items))
    );
  }

  public addToCart(item: CartItem): void {
    const existingItem = this._cartItems.value.find(cartItem => cartItem?.product?.id === item?.product?.id);
    if (existingItem) {
      existingItem.quantity = item.quantity;
    } else {
      this._cartItems.next([...this._cartItems.value, item]);
    }
  }

  public clearCart(): void {
    this._cartItems.next([]);
    this._products.value.forEach(product => {
      product.quantity = 0;
      this.productService.update(product).subscribe();
    });
  }

  public removeItem(productId: number): void {
    this._cartItems.next(this._cartItems.value.filter(item => item?.product?.id !== productId));
    const product = this._products.value.find(product => product.id === productId);
    if(product) {
        product.quantity = 0;
        this.productService.update(product).subscribe();
    }
  }

  public updateQuantity(productId: number, quantity: number): void {
    const existingItem = this._cartItems.value.find(cartItem => cartItem.product.id === productId);
    if (existingItem) {
      existingItem.quantity = quantity;
      this._cartItems.next([...this._cartItems.value]);
    }
  }
}