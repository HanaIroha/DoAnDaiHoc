export interface IOrderDetail {
    idOrderDetail?: number;
    idOrder?: number;
    idProduct?: number;
    quantity?:number;
    price?:number;
    createdAt?: Date;
    updatedAt?: Date;
  }
  
  export class OrderDetail implements IOrderDetail {
    constructor(
        public idOrderDetail?: number,
        public idOrder?: number,
        public idProduct?: number,
        public quantity?:number,
        public price?:number,
        public createdAt?: Date,
        public updatedAt?: Date,
    ) {}
  }
  