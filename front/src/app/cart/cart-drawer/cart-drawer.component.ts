import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CartItem } from '../data-access/cart.model';
import { Product } from 'app/products/data-access/product.model';
import { CartService } from '../data-access/cart.service';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { SidebarModule } from 'primeng/sidebar';



@Component({
  selector: 'app-cart-drawer',
  templateUrl: './cart-drawer.component.html',
  styleUrls: ['./cart-drawer.component.scss'],
  standalone: true,
  imports: [CommonModule, ButtonModule, SidebarModule]

})
export class CartDrawerComponent implements OnInit {
  @Input() visible: boolean = false;
  @Output() visibleChange = new EventEmitter<boolean>();
  cartItems: CartItem[] = [];
  products: Product[] = [];

  constructor(private cartService: CartService) {}

  ngOnInit(): void {
    this.cartService.cartItems.subscribe((items : CartItem[]) => {
      this.cartItems = items;
    });
    
  }


  clearCart(): void {
    this.cartService.clearCart();
    this.cartItems = [];
  }

  removeItem(productId: number): void {
    this.cartService.removeItem(productId);
    this.cartItems = this.cartItems.filter(item => item?.product?.id !== productId);
  }

  getTotal(): number {
    return this.cartItems.reduce((acc, item) => acc + item.product.price * item.quantity, 0);
  }

  closeDrawer() {
    this.visible = false;
    this.visibleChange.emit(this.visible);
  }
}