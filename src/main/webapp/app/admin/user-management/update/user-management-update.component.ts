import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { LANGUAGES } from 'app/config/language.constants';
import { DOCUMENTTYPE } from 'app/config/documentType.constants';
import { User } from '../user-management.model';
import { UserManagementService } from '../service/user-management.service';
import { GrillaManagementService } from '../service/grilla-management.service';

@Component({
  selector: 'jhi-user-mgmt-update',
  templateUrl: './user-management-update.component.html',
})
export class UserManagementUpdateComponent implements OnInit {
  user!: User;
  //grilla!: Grilla;
  languages = LANGUAGES;
  documenttypes = DOCUMENTTYPE;
  authorities: string[] = [];
  departamento: string[] = [];
  municipio: string[] = [];
  convenio: string[] = [];
  programa: string[] = [];
  departamentoName!: string;
  municipioName!: string;
  convenioName!: string;
  programaName!: string;

  isMunicipios = false;

  idDepartamento!: number;
  idConvenio!: number;
  validadorCelphone!: number;
  validadorEmail!: string;

  convenioNIT!: string;
  programaNIT!: string;

  isSaving = false;
  celphoneCredentialsError = false;
  emailCredentialsError = false;

  departamentoNumber = 0;

  editForm = this.fb.group({
    id: [],
    login: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(11)]],
    documentType: [[Validators.required]],
    celphone: [
      '',
      [Validators.maxLength(50), Validators.required, Validators.pattern('^[0-9!$&*+=?^_`{|}~.-]+@[0-9-]+(?:\\.[0-9-]+)*$|^[_.@0-9-]+$')],
    ],
    firstName: ['', [Validators.required]],
    lastName: ['', [Validators.maxLength(50), Validators.required]],
    email: ['', [Validators.minLength(5), Validators.maxLength(254), Validators.email]],

    convenio: [],
    programa: [],
    departamento: [],
    //departamento: [[Validators.maxLength(50)], [Validators.required]],
    municipio: [],

    isMunicipios: [],
    activated: [],
    langKey: [],
    authorities: [],
  });

  constructor(
    private userService: UserManagementService,
    private grillaService: GrillaManagementService,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ user }) => {
      if (user) {
        this.user = user;
        if (this.user.id === undefined) {
          this.user.activated = true;
        }
        this.updateForm(user);
      }
    });
    this.userService.authorities().subscribe(authorities => (this.authorities = authorities));
    this.userService.getDepartamentos().subscribe(departamentosName => (this.departamento = departamentosName));
    this.userService.getConvenios().subscribe(conveniosName => (this.convenio = conveniosName));
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    this.updateUser(this.user);
    if (this.user.id !== undefined) {
      this.userService.update(this.user).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      this.userService.create(this.user).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    }
  }

  sqlask(): void {
    this.updateDepartamento(this.user);
  }

  sqlmunicipio(): void {
    this.updateMunicipio(this.user);
  }

  sqlAddLocation(): void {
    this.addDepartamentoAndMunicipio(this.user);
  }

  sqlconvenio(): void {
    this.updateConvenio(this.user);
  }

  sqlprograma(): void {
    this.updatePrograma(this.user);
  }

  sqlAddAgreements(): void {
    this.addConvenioAndPrograma(this.user);
  }

  private updateForm(user: User): void {
    this.editForm.patchValue({
      id: user.id,
      login: user.login,
      documentType: user.documentType,

      convenio: user.convenio,
      programa: user.programa,
      departamento: user.departamento,
      municipio: user.municipio,
      celphone: user.celphone,
      firstName: user.firstName,
      lastName: user.lastName,
      email: user.email,
      activated: user.activated,
      langKey: user.langKey,
      authorities: user.authorities,
    });
  }

  private updateUser(user: User): void {
    user.login = this.editForm.get(['login'])!.value;

    user.documentType = this.editForm.get(['documentType'])!.value;

    this.validadorCelphone = this.editForm.get(['celphone'])!.value;
    if (this.validadorCelphone.toString().startsWith('3')) {
      user.celphone = this.editForm.get(['celphone'])!.value;
      this.celphoneCredentialsError = false;
    } else {
      this.celphoneCredentialsError = true;
    }

    user.firstName = this.editForm.get(['firstName'])!.value;

    // this.validadorFirstName = this.editForm.get(['firstName'])!.value;

    // user.firstName = (this.validadorFirstName).normalize("NFD").replace(/[\u0300-\u036f]/g,"");

    user.email = this.editForm.get(['email'])!.value;
    // if (this.validadorEmail.toString().endsWith('@supergiros.com.co')) {
    //   user.email = this.editForm.get(['email'])!.value;
    //   this.emailCredentialsError = false;
    // } else {
    //   this.emailCredentialsError = true;
    // }

    user.lastName = this.editForm.get(['lastName'])!.value;
    // user.email = this.editForm.get(['email'])!.value;

    user.activated = this.editForm.get(['activated'])!.value;
    user.langKey = this.editForm.get(['langKey'])!.value;
    user.authorities = this.editForm.get(['authorities'])!.value;

    user.isMunicipios = this.editForm.get(['isMunicipios'])!.value;
  }

  private updateDepartamento(user: User): void {
    const ad = this.editForm.get(['departamento'])!.value.toString();
    this.departamentoName = ad;
    this.userService.getIdDepartamentos(ad).subscribe(xx => (this.idDepartamento = xx));
  }

  private updateMunicipio(user: User): void {
    const ad = this.userService.getMunicipios(this.idDepartamento).subscribe(xxx => (this.municipio = xxx));
  }

  private addDepartamentoAndMunicipio(user: User): void {
    user.departamento = this.idDepartamento;
    const ad = this.editForm.get(['municipio'])!.value.toString();
    this.municipioName = ad.toString();

    this.userService.getIdMunicipios(ad).subscribe(xx => (user.municipio = xx));
  }

  private updateConvenio(user: User): void {
    const ad = this.editForm.get(['convenio'])!.value.toString();
    this.convenioName = ad;

    this.userService.getIdConvenios(ad).subscribe(xx => (this.idConvenio = xx));
    /* eslint no-console: ["error", { allow: ["warn", "error"] }] */
  }

  private updatePrograma(user: User): void {
    const ad = this.userService.getProgramas(this.idConvenio).subscribe(xxx => (this.programa = xxx));
  }

  private addConvenioAndPrograma(user: User): void {
    user.convenio = this.idConvenio;
    const ad = this.editForm.get(['programa'])!.value.toString();
    this.programaName = ad.toString();

    this.userService.getIdProgramas(ad).subscribe(xx => (user.programa = xx));
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }
}
