export interface IImage {
    idImage?: number;
    idProduct?: number;
    imageUrl?: string;
  }
  
  export class Image implements IImage {
    constructor(
        public idImage?: number,
        public idProduct?: number,
        public imageUrl?: string,
    ) {}
  }
  