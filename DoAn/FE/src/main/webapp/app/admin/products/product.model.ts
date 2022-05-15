export interface IProduct {
    idProduct?: number;
    idCategory?: number;
    idProducer?: number;
    name?: string;
    image?: string;
    code?: string;
    price?: number;
    salePercent?: number;
    lastPrice?: number;
    quantity?: number;
    imformation?: string;
    imformationDetails?: string;
    createdAt?: Date;
    updatedAt?: Date;
    isDisable?: boolean;
  }
  export class Product implements IProduct {
    constructor(
        public idProduct?: number,
        public idCategory?: number,
        public idProducer?: number,
        public name?: string,
        public image?: string,
        public code?: string,
        public price?: number,
        public salePercent?: number,
        public lastPrice?: number,
        public quantity?: number,
        public information?: string,
        public informationDetails?: string,
        public createdAt?: Date,
        public updatedAt?: Date,
        public isDisable?: boolean,
    ) {}
  }
  