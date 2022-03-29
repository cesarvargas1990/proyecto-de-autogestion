import { AfterViewInit, Component, OnInit } from '@angular/core';
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
export class UserManagementUpdateComponent implements OnInit, AfterViewInit {
  user!: User;

  udmmodel!: udmModel;

  municipiosListFull: string[] = [];
  departamentosListFull: string[] = [];

  departamentosListNameFull: string[] = [];
  municipiosListNameFull: string[] = [];

  municipiosListIDFull: number[] = [];

  convenioNameEdit: string[] = [];
  programaNameEdit: string[] = [];

  convenioNIT!: string;
  programaNIT!: string;

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

  idDepartamentos: number[] = [];
  idConvenio!: number;
  validadorCelphone!: number;
  validadorEmail!: string;

  isSaving = false;
  celphoneCredentialsError = false;
  emailCredentialsError = false;
  idCredentialsError = false;
  addLocationVerification = true;
  saveReady = true;

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

  ngAfterViewInit(): void {
    this.route.data.subscribe(({ user }) => {
      if (user) {
        this.user = user;
        this.updateForm(user);
      }
    });
  }

  ngOnInit(): void {
    this.route.data.subscribe(({ user }) => {
      if (user) {
        this.user = user;
        if (this.user.id === undefined) {
          this.user.activated = true;
        } else {
          this.userService.getDepartamentosName(this.user.id).subscribe(x => {
            this.departamentosListNameFull = x;
            //MUESTRA LOS MUNICIPIOS EN EL FRONTEND
            for (let index = 0; index < this.departamentosListNameFull.length; index++) {
              this.userService.getIdMultipleDepartamentos(this.departamentosListNameFull[index]).subscribe(xx => {
                this.municipiosListIDFull.push(xx);
                this.userService.getMultiplesMunicipios(this.municipiosListIDFull).subscribe(xxx => {
                  this.municipio = xxx;
                });
              });
            }
            this.departamentoName = this.departamentosListNameFull.toString();
            this.editForm.patchValue({
              departamento: this.departamentosListNameFull,
            });
          });

          this.userService.findMunicipiosNameByID(this.user.id).subscribe(x => {
            this.municipiosListNameFull = x;
            //this.municipioName = this.municipiosListNameFull.toString();
            this.editForm.patchValue({
              municipio: this.municipiosListNameFull,
            });
          });

          this.userService.findConvenioID(this.user.id).subscribe(x => {
            this.userService.getProgramas(Number(x)).subscribe(xx => {
              this.programa = xx;
              this.programaName = xx.toString();
              this.convenioName = 'DPS - Departamento para la Prosperidad Social';

              // for (let index = 0; index < xx.length; index++) {
              //   //const element = array[index];
              //   this.userService.findProgramasName(Number(this.user.id!),(xx[index])).subscribe(xxx =>{
              //     if((xxx) === ((this.user.programa)?.toString())){
              //     }
              //   });
              // }
            });
          });

          // this.userService.findProgramaName(this.user.id).subscribe(x =>{});
        }
      }
    });

    this.userService.getDepartamentos().subscribe(departamentosName => (this.departamento = departamentosName));
    this.userService.authorities().subscribe(authorities => (this.authorities = authorities));
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
        next: x => {
          this.onSaveSuccess();
          const udm = new udmModel(x.id, this.departamentosListFull, this.municipiosListFull);
          this.userService.MakeinsertUDM(udm).subscribe();
          this.municipiosListFull = [];
          this.departamentosListFull = [];
        },
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
      convenio: user.convenio === 1 ? 'DPS - Departamento para la Prosperidad Social' : user.convenio,
      programa: user.programa === 1 ? 'Devolución IVA' : user.programa,
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
    this.departamentoName = ad;
    this.userService.getIdMultiplesDepartamentos(ad).subscribe(xx => {
      //MUESTRA LOS MUNICIPIOS EN EL FRONTEND
      this.idDepartamentos = xx;
      this.userService.getMultiplesMunicipios(this.idDepartamentos).subscribe(xxx => (this.municipio = xxx));
    });
  }

  //Este botón ya no existe, llama la función SQLMUNICIPIO()
  private updateMunicipio(user: User): void {
    this.userService.getMultiplesMunicipios(this.idDepartamentos).subscribe(xxx => (this.municipio = xxx));
  }

  private addDepartamentoAndMunicipio(user: User): void {
    const ad = this.editForm.get(['municipio'])!.value;
    this.municipiosList = ad;

    // for (let index = 0; index < ad.length; index++) {
    //   if (ad[index]==='--TODOS--'){

    //   }
    // }
    this.municipioName = ad.toString();

    for (let index = 0; index < ad.length; index++) {
      this.userService.findDepartamentosIDByMunicipioName(ad[index]).subscribe(x => {
        this.idDepartamentos.push(Number(x));
        this.departamentosListFull.push(x);
      });
      this.userService.getIdMunicipios(ad[index]).subscribe(xx => {
        this.municipiosListId[index] = xx.toString();
      });
    }

    if (ad.length === this.municipio.length) {
      this.municipioName = 'Todos los Municipios';
    } else {
      user.departamentos = this.departamentoName;
    }

    if (this.municipioName === '-TODOS-') {
      this.addLocationVerification = true;
      this.saveReady = false;
      this.departamentosListFull = ['1000'];
      this.municipiosListFull = ['1'];
    } else {
      this.saveReady = true;
      this.addLocationVerification = false;
    }
  }

  private addLocation(): void {
    for (let index = 0; index < this.municipiosList.length; index++) {
      this.municipiosListFull.push(this.municipiosListId[index]);
    }
    this.saveReady = false;
  }

  private updateConvenio(user: User): void {
    const ad = this.editForm.get(['convenio'])!.value.toString();
    this.convenioName = ad;
    user.convenioName = this.convenioName;
    this.userService.getIdConvenios(ad).subscribe(xx => {
      this.idConvenio = xx;
      this.userService.getProgramas(this.idConvenio).subscribe(xxx => (this.programa = xxx));
    });
  }

  //El botón que lo llama esta comentado, el botón llama a la funcion SQLPROGRAMA()
  private updatePrograma(user: User): void {
    this.userService.getProgramas(this.idConvenio).subscribe(xxx => (this.programa = xxx));
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
