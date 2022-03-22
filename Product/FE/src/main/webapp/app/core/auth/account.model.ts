export class Account {
  constructor(
    public activated: boolean,
    public authorities: string[],
    public email: string,
    public fullname: string | null,
    public langKey: string,
    public address: string | null,
    public phonenumber: string | null,
    public login: string,
    public imageUrl: string | null
  ) {}
}
