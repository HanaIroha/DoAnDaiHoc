import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { PaymentService } from 'app/service/payment.service';
import { Payment } from '../payment.model';



@Component({
  selector: 'jhi-create-payment',
  templateUrl: './create-payment.component.html',
  styleUrls: ['./create-payment.component.scss']
})
export class CreatePaymentComponent implements OnInit {

  payment = new Payment();
  isSaving = false;

  editForm = this.fb.group({
    idPayment: [],
    name: [
      '',
      [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
      ],
    ],
    description: [
      '',
      [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
      ],
    ],
  });

  constructor(private paymentService: PaymentService, private route: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    // let id = this.route.snapshot.paramMap.get('id');
    //   this.paymentService.getOne(id).subscribe(res => {
    //     this.payment=res;
    //     this.updateForm(this.payment);
    //   });
    if(this.route.snapshot.paramMap.has('id')){
      let id = this.route.snapshot.paramMap.get('id');
      this.paymentService.getOne(id).subscribe(res => {
        this.payment=res;
        this.updateForm(this.payment);
      });
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    this.updatePayment(this.payment);
    if (this.payment.idPayment !== undefined) {
      this.paymentService.update(this.payment).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      this.paymentService.create(this.payment).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    }
  }

  private updateForm(payment: Payment): void {
    this.editForm.patchValue({
      idPayment: payment.idPayment,
      name: payment.name,
      description: payment.description,
    });
  }

  private updatePayment(payment: Payment): void {
    payment.name = this.editForm.get(['name'])!.value;
    payment.description = this.editForm.get(['description'])!.value;
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }

}
