import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Person } from '../model/person';
import { GenericService } from './generic.service';

@Injectable({
  providedIn: 'root'
})
export class PersonService extends GenericService<Person>{

  public personChange = new Subject<Person[]>;
  private messageChange = new Subject<string>;

  constructor(protected override http: HttpClient) {
    super(http,
          `${environment.apiBase}/persons`);
   }

  setMessageChange(message: string){
    this.messageChange.next(message);
  }

  getMessageChange(){
    return this.messageChange.asObservable();
  }
}
