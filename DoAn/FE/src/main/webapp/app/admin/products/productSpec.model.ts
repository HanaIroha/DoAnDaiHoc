export interface IProductSpec {
    idProductSpec?: number;
    name?: number;
    key?: string;
    value?: string;
  }
  
  export class ProductSpec implements IProductSpec {
    constructor(
        public idProductSpec?: number,
        public idProduct?: number,
        public key?: string,
        public value?: string,
    ) {}
  }