import { CommonModule } from "@angular/common";
import { Component, OnInit, inject, signal } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { CartService } from "app/cart/data-access/cart.service";
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { ProductFormComponent } from "app/products/ui/product-form/product-form.component";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { SelectItem } from 'primeng/api';
import { TagModule } from 'primeng/tag';
import { InputNumberModule } from "primeng/inputnumber";
import { Nullable } from "primeng/ts-helpers";

const emptyProduct: Product = {
  id: 0,
  code: "",
  name: "",
  description: "",
  image: "",
  category: "",
  price: 0,
  quantity: 0,
  internalReference: "",
  shellId: 0,
  inventoryStatus: "INSTOCK",
  rating: 0,
  createdAt: 0,
  updatedAt: 0,
};

@Component({
  selector: "app-product-list",
  templateUrl: "./product-list.component.html",
  styleUrls: ["./product-list.component.scss"],
  standalone: true,
  imports: [InputNumberModule, DataViewModule, CardModule, TagModule, ButtonModule, DialogModule, DropdownModule, ProductFormComponent, CommonModule, FormsModule],
})
export class ProductListComponent implements OnInit {
  private readonly productsService = inject(ProductsService);
  private readonly cartService = inject(CartService);

  public products: Product[] = [];
  public filteredProducts: Product[] = [];
  public categories: SelectItem[] = [];
  public selectedCategory: string | null = null;

  public isDialogVisible = false;
  public isCreation = false;
  public readonly editedProduct = signal<Product>(emptyProduct);

  ngOnInit() {
    
    this.productsService.products.subscribe(products => {
      this.products = products;
      this.filteredProducts = products;
      this.categories = this.getCategories(products);
      this.filterProducts();
    });
  }

  public onCreate() {
    this.isCreation = true;
    this.isDialogVisible = true;
    this.editedProduct.set(emptyProduct);
  }

  public onUpdate(product: Product) {
    this.isCreation = false;
    this.isDialogVisible = true;
    this.editedProduct.set(product);
  }

  public onDelete(product: Product) {
    this.productsService.delete(product.id).subscribe(() => {
      this.productsService.get().subscribe(products => {
        this.filteredProducts = products;
        this.filterProducts();
      });
    });
  }

  public onSave(product: Product) {
    if (this.isCreation) {
      this.productsService.create(product).subscribe(() => {
        this.productsService.get().subscribe(products => {
          this.filteredProducts = products;
          this.filterProducts();
        });
      });
    } else {
      this.productsService.update(product).subscribe(() => {
        this.productsService.get().subscribe(products => {
          this.filteredProducts = products;
          this.filterProducts();
        });
      });
    }
    this.closeDialog();
  }

  public onCancel() {
    this.closeDialog();
  }

  private closeDialog() {
    this.isDialogVisible = false;
  }

  public addToCart(product: Product, addedQuantity: Nullable<number>): void {
    const quantity = addedQuantity ?? 0;

    if (quantity <= 0) {
      console.error('Invalid quantity:', addedQuantity);
      return;
    }

    product.quantity = (product.quantity || 0) + quantity;
    this.cartService.addToCart({ product, quantity: product.quantity });
    this.productsService.update(product).subscribe(() => {
      this.filterProducts();
    });
  }

  public clearQuantity(product: Product): void {
    product.quantity = 0;
    this.cartService.removeItem(product.id);
    this.productsService.update(product).subscribe(() => {
      this.filterProducts();
    });
  }

  public getInventoryStatusClass(status: string): string {
    switch (status) {
      case 'INSTOCK':
        return 'bg-green-500 text-white';
      case 'LOWSTOCK':
        return 'bg-orange-500 text-white';
      case 'OUTOFSTOCK':
        return 'bg-red-500 text-white';
      default:
        return '';
    }
  }

  getSeverity(product: Product) {
    switch (product.inventoryStatus) {
        case 'INSTOCK':
            return 'success';
        case 'LOWSTOCK':
            return 'warning';
        case 'OUTOFSTOCK':
            return 'danger';
        default:
            return undefined;
    }
  }

  public getStars(rating: number): string[] {
    const maxRating = Math.min(rating, 5);
    const fullStars = Math.floor(maxRating);
    const halfStar = maxRating % 1 !== 0;
    const emptyStars = 5 - fullStars - (halfStar ? 1 : 0);

    return [
      ...Array(fullStars).fill('full'),
      ...(halfStar ? ['half'] : []),
      ...Array(emptyStars).fill('empty')
    ];
  }

  private getCategories(products: Product[]): SelectItem[] {
    const categories = products.map(product => product.category);
    const uniqueCategories = Array.from(new Set(categories));
    uniqueCategories.sort();

    uniqueCategories.unshift('All');
    return uniqueCategories.map(category => ({ label: category, value: category }));
  }

  public filterProducts(): void {
    if (this.selectedCategory && this.selectedCategory !== 'All') {
      this.filteredProducts = this.products.filter(product => product.category === this.selectedCategory);
    } else {
      this.filteredProducts = this.products;
    }
  }
}