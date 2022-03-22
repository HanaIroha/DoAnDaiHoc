export interface IPayment {
    idPayment?: number;
    name?: string;
    description?: string;
    createdAt?: Date;
    updatedAt?: Date;
  }
  
  export class Payment implements IPayment {
    constructor(
        public idPayment?: number,
        public name?: string,
        public description?: string,
        public createdAt?: Date,
        public updatedAt?: Date,
    ) {}
  }
  