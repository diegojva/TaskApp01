import { Person } from "./person";
import { SaleDetail } from "./saleDetail";

export class Sale {
  idSale: number;
  person: Person;
  dateTime: string;
  total?: number;
  details: SaleDetail[];
}
