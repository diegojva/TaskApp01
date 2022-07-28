import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Sale } from '../model/sale';

@Injectable({
  providedIn: 'root'
})
export class SaleService {

  private url: string = `${environment.apiBase}/sales`;

  constructor(private http: HttpClient) { }

  saveTransactional(sale: Sale){
    return this.http.post(this.url, sale);
  }
}
