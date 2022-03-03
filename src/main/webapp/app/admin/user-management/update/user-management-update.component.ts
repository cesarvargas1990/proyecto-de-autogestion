import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { LANGUAGES } from 'app/config/language.constants';
import { DOCUMENTTYPE } from 'app/config/documentType.constants';
import { User, udmModel } from '../user-management.model';
import { UserManagementService } from '../service/user-management.service';
import { GrillaManagementService } from '../service/grilla-management.service';

@Component({
  selector: 'jhi-user-mgmt-update',
  templateUrl: './user-management-update.component.html',
})
export class UserManagementUpdateComponent implements OnInit {
  user!: User;

  udmmodel!: udmModel;

  municipiosListFull: string[] = [];
  departamentosListFull: string[] = [];

  municipiosListId: string[] = [];
  municipiosList: string[] = [];
  departamentosList!: string;

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
    documentType: ['', [Validators.required]],
    celphone: [
      '',
      [Validators.maxLength(50), Validators.required, Validators.pattern('^[0-9!$&*+=?^_`{|}~.-]+@[0-9-]+(?:\\.[0-9-]+)*$|^[_.@0-9-]+$')],
    ],
    firstName: ['', [Validators.required]],
    lastName: ['', [Validators.maxLength(50), Validators.required]],
    email: ['', [Validators.minLength(5), Validators.maxLength(254), Validators.email, Validators.required]],

    convenio: ['', [Validators.required]],
    programa: ['', [Validators.required]],
    departamento: ['', [Validators.required]],
    municipio: ['', [Validators.required]],

    isMunicipios: [],
    activated: [],
    langKey: [],
    authorities: ['', [Validators.required]],
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
        next: x => {
          this.onSaveSuccess();
          const udm = new udmModel(x.id, this.departamentosListFull, this.municipiosListFull);
          this.userService.MakeinsertUDM(udm).subscribe();
          this.municipiosListFull = [];
          this.departamentosListFull = [];
        },
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

  sqlAddNewLocation(): void {
    this.addLocation();
  }

  private updateForm(user: User): void {
    this.editForm.patchValue({
      id: user.id,
      login: user.login,
      documentType: user.documentType,

      convenio: user.convenio,
      // convenio: user.convenio === 1 ?"DPS – Departamento para la Prosperidad Social": user.convenio,
      //convenio: user.convenioName,

      programa: user.programa,
      departamento: user.departamentoName,
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
    user.email = this.editForm.get(['email'])!.value;
    // if (this.validadorEmail.toString().endsWith('@supergiros.com.co')) {
    //   user.email = this.editForm.get(['email'])!.value;
    //   this.emailCredentialsError = false;
    // } else {
    //   this.emailCredentialsError = true;
    // }

    user.lastName = this.editForm.get(['lastName'])!.value;
    user.activated = this.editForm.get(['activated'])!.value;
    user.langKey = this.editForm.get(['langKey'])!.value;
    user.authorities = this.editForm.get(['authorities'])!.value;
  }

  private updateDepartamento(user: User): void {
    const ad = this.editForm.get(['departamento'])!.value.toString();

    user.departamentoName = ad;

    //  /* eslint no-console: ["error", { allow: ["warn", "error"] }] */
    //  console.log("FUUUUUUUUUUUUUUUUUUUUUUUUUL");
    //  console.log(user.departamentoName);

    this.departamentoName = ad;
    this.userService.getIdDepartamentos(ad).subscribe(xx => (this.idDepartamento = xx));
  }

  private updateMunicipio(user: User): void {
    this.userService.getMunicipios(this.idDepartamento).subscribe(xxx => (this.municipio = xxx));
  }

  private addDepartamentoAndMunicipio(user: User): void {
    user.departamento = this.idDepartamento;
    this.departamentosList = this.idDepartamento.toString();
    const ad = this.editForm.get(['municipio'])!.value;
    this.municipiosList = ad;

    this.municipioName = ad.toString();
    for (let index = 0; index < ad.length; index++) {
      this.userService.getIdMunicipios(ad[index]).subscribe(xx => (this.municipiosListId[index] = xx.toString()));

      this.departamentosListFull.push(this.idDepartamento.toString());
      /* eslint no-console: ["error", { allow: ["warn", "error"] }] */
      // console.log("DEPARTAMENTOOOOOOOOOOOOOOOOOOOOOOOOOOOOS");
      // console.log(this.departamentosListFull);
    }
  }

  private addLocation(): void {
    for (let index = 0; index < this.municipiosList.length; index++) {
      this.municipiosListFull.push(this.municipiosListId[index]);
    }

    // /* eslint no-console: ["error", { allow: ["warn", "error"] }] */
    //   console.log("FUUUUUUUUUUUUUUUUUUUUUUUUUL");
    //   console.log(this.departamentosListFull);
    //   console.log(this.municipiosListFull);
  }

  private updateConvenio(user: User): void {
    const ad = this.editForm.get(['convenio'])!.value.toString();

    this.convenioName = ad;
    user.convenioName = this.convenioName;

    this.userService.getIdConvenios(ad).subscribe(xx => (this.idConvenio = xx));
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
