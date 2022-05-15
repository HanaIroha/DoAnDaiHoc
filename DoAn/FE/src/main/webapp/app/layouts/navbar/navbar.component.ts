import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { LANGUAGES } from 'app/config/language.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { EntityNavbarItems } from 'app/entities/entity-navbar-items';
import { Category } from 'app/admin/categories/category.model';
import { CategoryService } from 'app/service/category.service';
import { Banner } from 'app/admin/banners/banner.model';
import { BannerService } from 'app/service/banner.service';

@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {

  imagePath = "\\content\\imageStorage\\";
  defaultImage = "\\content\\imageStorage\\default.jpg";

  inProduction?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  openAPIEnabled?: boolean;
  version = '';
  account: Account | null = null;
  entitiesNavbarItems: any[] = [];
  data = [];

  isIndex?: boolean;

  cartNumber:number;

  categories: Category[];

  banners: Banner[];

  constructor(
    private loginService: LoginService,
    private translateService: TranslateService,
    private sessionStorageService: SessionStorageService,
    private accountService: AccountService,
    private profileService: ProfileService,
    private router: Router,
    private categoryService: CategoryService,
    private bannerService: BannerService,
  ) {
    if (VERSION) {
      this.version = VERSION.toLowerCase().startsWith('v') ? VERSION : `v${VERSION}`;
    }
  }

  ngOnInit(): void {
      this.entitiesNavbarItems = EntityNavbarItems;
      this.profileService.getProfileInfo().subscribe(profileInfo => {
        this.inProduction = profileInfo.inProduction;
        this.openAPIEnabled = profileInfo.openAPIEnabled;
        this.changeLanguage("vi");
      });

      this.accountService.getAuthenticationState().subscribe(account => {
        this.account = account;
      });

      this.categoryService.getAll().subscribe(res=>{
        this.categories = res;
      })

      this.bannerService.getAll().subscribe(res=>{
        this.banners = res;
      })

      this.cartNumber=0;

      if (!localStorage['cart']) this.data = [];
      else this.data = JSON.parse(localStorage['cart']); 
      if (!(this.data instanceof Array)) this.data = [];
      for (let i = 0; i< this.data.length; i++){
        this.cartNumber++;
      }
      if(this.router.url === '/'){
          this.isIndex = true;
      }
      else{
        this.isIndex = false;
      }
  }

  hideBanner(): void{
    this.isIndex = false;
  }
  showBanner(): void{
    this.isIndex = true;
  }

  changeLanguage(languageKey: string): void {
    this.sessionStorageService.store('locale', languageKey);
    this.translateService.use(languageKey);
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  logout(): void {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  search(value): void {
    this.router.navigate(['/shop'],{queryParams: {f1: value}});
  }
}
