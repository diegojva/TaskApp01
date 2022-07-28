import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/model/product';
import { ProductService } from 'src/app/service/product.service';
import { switchMap } from 'rxjs';

@Component({
  selector: 'app-product-edit',
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.css']
})
export class ProductEditComponent implements OnInit {

  id: number;
  isEdit: boolean;
  form: FormGroup;

  constructor(private routeActivated : ActivatedRoute,
              private productService: ProductService,
              private router : Router) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      'idProduct' : new FormControl(0),
      'name' : new FormControl('',[Validators.required,Validators.minLength(3)]),
      'brand' : new FormControl('',[Validators.required,Validators.minLength(3)]),
      'price' : new FormControl('',[Validators.required,Validators.min(1)]),
      'photoUrl': new FormControl('',[Validators.required]),
    });
    this.routeActivated.params.subscribe(data =>{
      this.id = data['id'];
      this.isEdit = data['id'] != null;
      this.initForm();
    })
  }

  initForm(){
    if(this.isEdit){
      this.productService.findById(this.id).subscribe(data =>{
        this.form.patchValue(data);
      });
    }
  }

  get f(){
    return this.form.controls;
  }

  operate(){
    if(this.form.invalid){ return; };

    let product = new Product();
    product = this.form.value;

    if(this.isEdit){
      // UPDATE Product
      this.productService.update(product).pipe(switchMap(()=>{
        return this.productService.findAll();
      }))
      .subscribe(data => {
        this.productService.productChange.next(data);
        this.productService.setMessageChange('UPDATE!')
      });

    }else {
      // INSERT Product
      this.productService.save(product).pipe(switchMap(() =>{
        return this.productService.findAll();
      }))
      .subscribe(data => {
        this.productService.productChange.next(data);
        this.productService.setMessageChange('CREATED!')
      });
    }
    this.router.navigate(['/pages/product'])
  }

}
