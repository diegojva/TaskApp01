import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { switchMap } from 'rxjs';
import { Product } from 'src/app/model/product';
import { ProductService } from 'src/app/service/product.service';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

  displayedColumns: string[] = ['idProduct', 'name', 'brand','price', 'photoUrl','actions'];
  dataSource: MatTableDataSource<Product>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private productService: ProductService,
              private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.productService.productChange.subscribe(products =>{
      this.createTable(products);
    });

    this.productService.getMessageChange().subscribe( data  =>{
      this._snackBar.open(data, 'OK', {duration: 3000});
    })

    this.productService.findAll().subscribe(products => {
      this.createTable(products);
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  delete(idProduct : number){
    let isSure = confirm('Are you sure you want to delete this product?')
    if(isSure){
      this.productService.delete(idProduct).pipe(switchMap(()=>{
        return this.productService.findAll();
      }))
      .subscribe(data => {
        this.productService.productChange.next(data);
        this.productService.setMessageChange('DELETED!')
      })
    }
  }

  createTable(products: Product[]){
    this.dataSource = new MatTableDataSource(products);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
}
