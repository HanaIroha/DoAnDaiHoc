export interface IOrder {
    idOrder?: number;
    login?: string;
    orderCode?: string;
    name?:string;
    phone?:string;
    address?:string;
    orderStatus?:number;
    idPayment?:number;
    createdAt?: Date;
    updatedAt?: Date;
  }
  
  export class Order implements IOrder {
    constructor(
        public idOrder?: number,
        public login?: string,
        public orderCode?: string,
        public name?:string,
        public phone?:string,
        public address?:string,
        public orderStatus?:number,
        public idPayment?:number,
        public createdAt?: Date,
        public updatedAt?: Date,
    ) {}
  }
  