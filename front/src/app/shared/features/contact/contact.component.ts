import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { ButtonModule } from 'primeng/button';
import { CommonModule } from '@angular/common';
import { InputGroupModule } from 'primeng/inputgroup';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { MessagesModule } from 'primeng/messages';
import { Message } from 'primeng/api';
import { CardModule } from 'primeng/card';


@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss'],
  standalone: true,
  imports: [CardModule, ReactiveFormsModule, InputGroupModule, InputGroupAddonModule, MessagesModule, InputTextModule, InputTextareaModule, ButtonModule, CommonModule]
})
export class ContactComponent {
  contactForm: FormGroup;
  successMessage: Message[] = [];

  constructor(private fb: FormBuilder) {
    this.contactForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      message: ['', [Validators.required, Validators.maxLength(300)]]
    });
  }

  onSubmit() {
    if (this.contactForm.valid) {
      // Logique pour envoyer le message, par exemple avec un service HTTP
      this.successMessage = [{ severity: 'success', summary: 'Demande de contact envoyée avec succès.' }];
      this.contactForm.reset();
    }
  }

  clearMessages() {
    this.successMessage = [];
    }
}