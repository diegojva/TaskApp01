import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatStepper } from '@angular/material/stepper';
import { Sale } from 'src/app/model/sale';
import { SaleDetail } from 'src/app/model/saleDetail';
import * as moment from 'moment';
import { PersonService } from 'src/app/service/person.service';
import { ProductService } from 'src/app/service/product.service';
import { SaleService } from 'src/app/service/sale.service';
import { Person } from 'src/app/model/person';
import { Product } from 'src/app/model/product';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'app-sale',
  templateUrl: './sale.component.html',
  styleUrls: ['./sale.component.css']
})
export class SaleComponent implements OnInit {

  isLinear: boolean;
  firstFormGroup: FormGroup;

  persons: Person[];
  products: Product[];

  maxDate: Date = new Date();
  details: SaleDetail[] = [];
  productSelected: Product;

  total: number = 0;

  @ViewChild('stepper') stepper: MatStepper;

  constructor(
    private personService: PersonService,
    private productService: ProductService,
    private saleService: SaleService,
    private formBuilder: FormBuilder,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.firstFormGroup = this.formBuilder.group({
      'person': ['', Validators.required],
      'dateTime': [new Date(), Validators.required],
      'quantity': ['',[Validators.required, Validators.min(1)]],
    });

    this.loadInitialData();
  }

  simulateTotal(){
    this.total = 0;

    this.details.forEach(detail => {
      this.total += detail.product?.price * detail?.quantity;
    });

    return this.total;
  }

  loadInitialData() {
    this.personService.findAll().subscribe(data => this.persons = data);
    this.productService.findAll().subscribe(data => this.products = data);
  }

  addProduct() {
    let isValid = this.firstFormGroup.value['quantity'];
    if(isValid > 0) {
      let detail = new SaleDetail();
      detail.product = this.productSelected;
      detail.quantity = this.firstFormGroup.value['quantity'];

      this.details.push(detail);
    }else {
      this.snackBar.open('PLEASE, SELECT A VALID QUANTITY', 'INFO', { duration: 3000 });
    }
  }

  removeDetail(index: number) {
    this.details.splice(index, 1);
  }

  selectProduct(product: Product) {
    this.productSelected = product;
  }

  nextManualStep() {
    this.simulateTotal();
    this.stepper.next();
  }

  save() {
    let sale = new Sale();
    sale.person = this.firstFormGroup.value['person'];
    sale.details = this.details;

    sale.dateTime = moment(this.firstFormGroup.value['dateTime']).format('YYYY-MM-DDTHH:mm:ss');

    this.saleService.saveTransactional(sale).subscribe(() => {
      this.snackBar.open('SUCCESSFULL', 'INFO', { duration: 2000 });
      setTimeout(() => {
        this.cleanControls();
      }, 2000);

    });
  }

  cleanControls() {
    this.firstFormGroup.reset();
    this.stepper.reset();
    this.details = [];
    this.productSelected = undefined;
  }

  get f() {
    return this.firstFormGroup.controls;
  }

}
