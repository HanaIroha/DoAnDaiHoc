import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ProducerService } from 'app/service/producer.service';
import { Producer } from '../producer.model';

@Component({
  selector: 'jhi-create-producer',
  templateUrl: './create-producer.component.html',
  styleUrls: ['./create-producer.component.scss']
})
export class CreateProducerComponent implements OnInit {

  producer = new Producer();
  isSaving = false;

  editForm = this.fb.group({
    idProducer: [],
    name: [
      '',
      [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
      ],
    ],
  });

  constructor(private producerService: ProducerService, private route: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    // let id = this.route.snapshot.paramMap.get('id');
    //   this.producerService.getOne(id).subscribe(res => {
    //     this.producer=res;
    //     this.updateForm(this.producer);
    //   });
    if(this.route.snapshot.paramMap.has('id')){
      let id = this.route.snapshot.paramMap.get('id');
      this.producerService.getOne(id).subscribe(res => {
        this.producer=res;
        this.updateForm(this.producer);
      });
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    this.updateProducer(this.producer);
    if (this.producer.idProducer !== undefined) {
      this.producerService.update(this.producer).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      this.producerService.create(this.producer).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    }
  }

  private updateForm(producer: Producer): void {
    this.editForm.patchValue({
      idProducer: producer.idProducer,
      name: producer.name,
    });
  }

  private updateProducer(producer: Producer): void {
    producer.name = this.editForm.get(['name'])!.value;
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }

}
