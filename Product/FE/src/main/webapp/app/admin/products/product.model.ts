export interface IProduct {
    idProduct?: number;
    idCategory?: number;
    idProducer?: number;
    name?: string;
    image?: string;
    code?: string;
    price?: number;
    salePercent?: number;
    quantity?: number;
    supportSim?: string;
    monitor?: string;
    color?: string;
    frontCamera?: string;
    rearCamera?: string;
    cPU?: string;
    gPU?: string;
    rAM?: string;
    rOM?: string;
    oS?: string;
    pin?: string;
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
        public quantity?: number,
        public supportSim?: string,
        public monitor?: string,
        public color?: string,
        public frontCamera?: string,
        public rearCamera?: string,
        public cPU?: string,
        public gPU?: string,
        public rAM?: string,
        public rOM?: string,
        public oS?: string,
        public pin?: string,
        public informationDetails?: string,
        public createdAt?: Date,
        public updatedAt?: Date,
        public isDisable?: boolean,
    ) {}
  }
  