export interface IUser {
  id?: number;
  login?: string;
  fullname?:string;
  firstName?: string | null;
  lastName?: string | null;
  phonenumber?: string;
  address?:string;
  email?: string;
  activated?: boolean;
  langKey?: string;
  authorities?: string[];
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
}

export class User implements IUser {
  constructor(
    public id?: number,
    public login?: string,
    public fullname?: string,
    public firstName?: string | null,
    public lastName?: string | null,
    public address?:string,
    public phonenumber?: string,
    public email?: string,
    public activated?: boolean,
    public langKey?: string,
    public authorities?: string[],
    public createdBy?: string,
    public createdDate?: Date,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Date
  ) {}
}
