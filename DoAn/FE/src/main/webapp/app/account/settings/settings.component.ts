import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { LANGUAGES } from 'app/config/language.constants';

@Component({
  selector: 'jhi-settings',
  templateUrl: './settings.component.html',
})
export class SettingsComponent implements OnInit {
  account!: Account;
  success = false;
  languages = LANGUAGES;
  url: any;
  imagePath = "\\content\\imageStorage\\";
  defaultImage = "\\content\\imageStorage\\default.jpg";
  userAvatar;
  
  settingsForm = this.fb.group({
    fullname: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    address: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(100)]],
    phone: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(20)]],
    email: [undefined, [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
  });

  constructor(private accountService: AccountService, private fb: FormBuilder, private translateService: TranslateService) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.settingsForm.patchValue({
          fullname: account.fullname,
          address: account.address,
          phone: account.phonenumber,
          email: account.email,
        });
        if(account.imageUrl)
          this.url = this.imagePath + account.imageUrl;
        // alert(account.fullname + account.address + account.email + account.phonenumber);
        this.account = account;
      }
    });
  }

  save(): void {
    this.success = false;
    this.account.fullname = this.settingsForm.get('fullname')!.value;
    this.account.address = this.settingsForm.get('address')!.value;
    this.account.phonenumber = this.settingsForm.get('phone')!.value;
    this.account.email = this.settingsForm.get('email')!.value;
    console.log(this.account);

    this.accountService.save(this.account).subscribe(() => {
      this.success = true;

      this.accountService.authenticate(this.account);

      const formData = new FormData();
      formData.append('image', this.userAvatar);
      formData.append('login',this.account.login);
      this.accountService.updateAvatar(formData).subscribe(res=>{});

      if (this.account.langKey !== this.translateService.currentLang) {
        this.translateService.use(this.account.langKey);
      }
    });
  }

  preview(files) {
    if (files.length === 0)
      return;
 
    var mimeType = files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      return;
    }
    
    this.userAvatar = files[0];
 
    var reader = new FileReader();
    // this.imagePath = files;
    reader.readAsDataURL(files[0]); 
    reader.onload = (_event) => { 
      this.url = reader.result; 
    }
  }

}
