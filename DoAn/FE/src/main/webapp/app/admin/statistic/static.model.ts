export interface IStaticItem {
    ordercount?: number | null;
    itemcount?: number | null;
    value?: number | null;
  }
  
  export class StaticItem implements IStaticItem {
    constructor(
        public ordercount?: number | null,
        public itemcount?: number | null,
        public value?: number | null,
    ) {}
  }