import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { switchMap } from 'rxjs';
import { Person } from 'src/app/model/person';
import { PersonService } from 'src/app/service/person.service';

@Component({
  selector: 'app-person',
  templateUrl: './person.component.html',
  styleUrls: ['./person.component.css']
})
export class PersonComponent implements OnInit {

  displayedColumns: string[] = ['idPerson', 'firstName', 'lastName','actions'];
  dataSource: MatTableDataSource<Person>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private personService: PersonService,
              private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.personService.personChange.subscribe(persons =>{
      this.createTable(persons);
    });

    this.personService.getMessageChange().subscribe( data  =>{
      this._snackBar.open(data, 'OK', {duration: 3000});
    })

    this.personService.findAll().subscribe(persons => {
      this.createTable(persons);
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  delete(idPerson : number){
    let isSure = confirm('Are you sure you want to delete this person?')
    if(isSure){
      this.personService.delete(idPerson).pipe(switchMap(()=>{
        return this.personService.findAll();
      }))
      .subscribe(data => {
        this.personService.personChange.next(data);
        this.personService.setMessageChange('DELETED!')
      })
    }
  }

  createTable(persons: Person[]){
    this.dataSource = new MatTableDataSource(persons);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

}
