import {
  Component,
  OnInit,
} from "@angular/core";
import { RouterModule } from "@angular/router";
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import { CartDrawerComponent } from "./cart/cart-drawer/cart-drawer.component";
import { CartService } from "./cart/data-access/cart.service";
import { CartItem } from "./cart/data-access/cart.model";
import { CommonModule } from "@angular/common";
import { ContactComponent } from "./shared/features/contact/contact.component";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [CommonModule, RouterModule, SplitterModule, ToolbarModule, PanelMenuComponent, CartDrawerComponent, ContactComponent],
})
export class AppComponent implements OnInit{

  drawerVisible = false;
  cartItemCount = 0;

  title = "ALTEN SHOP";

  constructor(private cartService: CartService) {}

  ngOnInit(): void {
    this.cartService.cartItems.subscribe((items: CartItem[]) => {
      this.cartItemCount = items.length;
    });
  }

  toggleDrawer() {
    this.drawerVisible = !this.drawerVisible;
  }
}
