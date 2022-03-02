import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { UserManagementService } from '../service/user-management.service';
import { User } from '../user-management.model';
import { UserManagementDeleteDialogComponent } from '../delete/user-management-delete-dialog.component';
import { DOCUMENTTYPE } from 'app/config/documentType.constants';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'jhi-user-mgmt',
  templateUrl: './user-management.component.html',
})
export class UserManagementComponent implements OnInit {
  currentAccount: Account | null = null;
  users: User[] | null = null;

  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  documenttypes = DOCUMENTTYPE;
  user!: User;
  isShown!: boolean;
  isShownlist!: boolean;
  aplicarfiltro!: string;

  loginString!: string;
  documentTypeString!: string;
  searchCredentialsError = false;
  activated!: boolean;
  desactivated!: boolean;
  aplicarFiltro = true;
  removerFiltro!: boolean;

  editForm = this.fb.group({
    documentType: [[Validators.required]],
    login: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
  });

  constructor(
    private userService: UserManagementService,
    private accountService: AccountService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal,
    private fb: FormBuilder,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    this.handleNavigation();
    //this.updateForm(user);
  }

  setActive(user: User, isActivated: boolean): void {
    this.userService.update({ ...user, activated: isActivated }).subscribe();
  }

  trackIdentity(index: number, item: User): number {
    return item.id!;
  }

  deleteUser(user: User): void {
    const modalRef = this.modalService.open(UserManagementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.user = user;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }

  loadAll(): void {
    this.isLoading = true;
    this.isShownlist = true;

    this.userService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe({
        next: (res: HttpResponse<User[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers);
        },
        error: () => (this.isLoading = false),
      });
  }

  transition(): void {
    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute.parent,
      queryParams: {
        page: this.page,
        sort: `${this.predicate},${this.ascending ? ASC : DESC}`,
      },
    });
  }

  toggleShow(): void {
    this.documentTypeString = this.editForm.get(['documentType'])!.value.toString();
    this.loginString = this.editForm.get(['login'])!.value;

    this.userService.find(this.loginString).subscribe({
      next: xx => {
        if (xx.documentType === this.documentTypeString) {
          this.user = xx;
          this.isShown = !this.isShown;
          this.isShownlist = !this.isShownlist;
          this.searchCredentialsError = false;
          this.activated = xx.activated!;
          this.removerFiltro = this.aplicarFiltro;
          this.aplicarFiltro = !this.removerFiltro;
        } else {
          this.searchCredentialsError = true;
        }
      },
    });
  }

  activateOrdesactivate(user: User): void {
    this.desactivated = this.activated;

    this.activated = !this.activated;

    this.setActive(user, this.activated);
  }

  search(): void {
    // this.userService.find(this.editForm.value.login).subscribe(xx =>
    //   this.user = xx);
    const a = 1;
  }

  // private updateForm(user: User): void {

  //   this.editForm.patchValue({
  //     loginSearch: user.loginSearch,
  //     documentTypeSearch: user.documentTypeSearch,
  //   });

  // }

  // private updateUser(user: User): void {

  //   user.documentTypeSearch = this.editForm.get(['documentTypeSearch'])!.value;
  //   user.loginSearch = this.editForm.get(['loginSearch'])!.value;

  // }

  private handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(([data, params]) => {
      const page = params.get('page');
      this.page = +(page ?? 1);
      const sort = (params.get(SORT) ?? data['defaultSort']).split(',');
      this.predicate = sort[0];
      this.ascending = sort[1] === ASC;
      this.loadAll();
    });
  }

  private sort(): string[] {
    const result = [`${this.predicate},${this.ascending ? ASC : DESC}`];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private onSuccess(users: User[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.users = users;
  }
}
