import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Person } from 'src/app/model/person';
import { PersonService } from 'src/app/service/person.service';
import { switchMap } from 'rxjs';

@Component({
  selector: 'app-person-edit',
  templateUrl: './person-edit.component.html',
  styleUrls: ['./person-edit.component.css']
})
export class PersonEditComponent implements OnInit {

  id: number;
  isEdit: boolean;
  form: FormGroup;

  constructor(private routeActivated : ActivatedRoute,
              private personService: PersonService,
              private router : Router) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      'idPerson' : new FormControl(0),
      'firstName' : new FormControl('',[Validators.required,Validators.minLength(3)]),
      'lastName' : new FormControl('',[Validators.required,Validators.minLength(3)]),
    });
    this.routeActivated.params.subscribe(data =>{
      this.id = data['id'];
      this.isEdit = data['id'] != null;
      this.initForm();
    })
  }

  initForm(){
    if(this.isEdit){
      this.personService.findById(this.id).subscribe(data =>{
        this.form.patchValue(data);
      });
    }
  }

  get f(){
    return this.form.controls;
  }

  operate(){
    if(this.form.invalid){ return; };

    let person = new Person();
    person = this.form.value;

    if(this.isEdit){
      // UPDATE Person
      this.personService.update(person).pipe(switchMap(()=>{
        return this.personService.findAll();
      }))
      .subscribe(data => {
        this.personService.personChange.next(data);
        this.personService.setMessageChange('CREATED!')
      });

    }else {
      // INSERT Person
      this.personService.save(person).pipe(switchMap(() =>{
        return this.personService.findAll();
      }))
      .subscribe(data => {
        this.personService.personChange.next(data);
        this.personService.setMessageChange('CREATED!')
      });
    }
    this.router.navigate(['/pages/person'])
  }

}
