export interface IProducer {
    idProducer?: number;
    name?: string;
    createdAt?: Date;
    updatedAt?: Date;
  }
  
  export class Producer implements IProducer {
    constructor(
        public idProducer?: number,
        public name?: string,
        public createdAt?: Date,
        public updatedAt?: Date,
    ) {}
  }
  