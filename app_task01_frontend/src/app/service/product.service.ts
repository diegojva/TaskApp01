import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Product } from '../model/product';
import { GenericService } from './generic.service';

@Injectable({
  providedIn: 'root'
})
export class ProductService extends GenericService<Product> {

  public productChange = new Subject<Product[]>;
  private messageChange = new Subject<string>;

  constructor(protected override http: HttpClient) {
    super(http,
          `${environment.apiBase}/products`);
   }

  setMessageChange(message: string){
    this.messageChange.next(message);
  }

  getMessageChange(){
    return this.messageChange.asObservable();
  }
}
