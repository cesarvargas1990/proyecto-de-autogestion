import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { LANGUAGES } from 'app/config/language.constants';
import { DOCUMENTTYPE } from 'app/config/documentType.constants';
import { User, udmModel } from '../user-management.model';
import { UserManagementService } from '../service/user-management.service';
import { GrillaManagementService } from '../service/grilla-management.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { loadingModalComponent } from '../modal/loadingModal.component';
import { timeout } from 'rxjs';

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
  municipiosListNameFullEmpty: string[] = [];

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

  lengthDepartamentoList!: number;

  idDepartamentos: number[] = [];
  idDepartamentos2: number[] = [];
  nameDepartamentos: string[] = [];
  nameDepartamentos2: string[] = [];

  idConvenio!: number;
  validadorCelphone!: number;
  validadorEmail!: string;

  dropdownList: string[] = [];
  selectedItems: string[] = [];
  dropdownSettings = {};
  dropdownSettingsdocumenttypes = {};
  dropdownSettingsDepartamento = {};

  isSaving = false;
  celphoneCredentialsError = false;
  emailCredentialsError = false;
  idCredentialsError = false;
  idMunicipiosError = false;
  addMunicipiosVerification = true;
  addLocationVerification = true;
  addLocationVerification2 = true;
  EditOrCreate = true;

  saveReady = true;
  verificationMunicipiosFinish = false;

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
    private fb: FormBuilder,
    private modalService: NgbModal
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
      this.departamentoName = this.municipiosListNameFull.toString();
      if (user) {
        this.user = user;
        if (this.user.id === undefined) {
          this.user.activated = true;
          this.EditOrCreate = true;
        } else {
          this.EditOrCreate = false;

          this.userService.getDepartamentosName(this.user.id).subscribe(x => {
            this.departamentosListNameFull = x;
            //MUESTRA LOS MUNICIPIOS EN EL FRONTEND

            //INICIA MODAL
            this.modalService.open(loadingModalComponent);

            for (let index = 0; index < this.departamentosListNameFull.length; index++) {
              this.userService.getIdMultipleDepartamentos(this.departamentosListNameFull[index]).subscribe(xx => {
                this.municipiosListIDFull.push(xx);
                this.userService.getMultiplesMunicipios(this.municipiosListIDFull).subscribe(xxx => {
                  this.municipio = xxx;
                  if (index === this.departamentosListNameFull.length - 1) {
                    //TERMINA MODAL
                    this.modalService.dismissAll();
                  }
                });
              });
            }
            this.departamentoName = this.departamentosListNameFull.toString();
            this.selectedItems = this.departamentosListNameFull;
            this.editForm.patchValue({
              departamento: this.departamentosListNameFull,
            });
          });

          this.userService.findMunicipiosNameByID(this.user.id).subscribe(x => {
            this.municipiosListNameFull = x;
            this.editForm.patchValue({
              municipio: this.municipiosListNameFull,
            });
          });

          this.userService.findConvenioID(this.user.id).subscribe(x => {
            this.userService.getProgramas(Number(x)).subscribe(xx => {
              this.programa = xx;
              this.programaName = xx.toString();
              this.convenioName = 'DPS - Departamento para la Prosperidad Social';
            });
          });
        }
      }
    });
    this.addLocationVerification = true;
    this.addLocationVerification2 = true;
    this.addMunicipiosVerification = true;
    this.userService.getDepartamentos().subscribe(departamentosName => {
      this.departamento = departamentosName;
      this.lengthDepartamentoList = departamentosName.length;
      this.dropdownList = departamentosName;
    });
    this.userService.authorities().subscribe(authorities => (this.authorities = authorities));
    this.userService.getConvenios().subscribe(conveniosName => (this.convenio = conveniosName));

    this.dropdownSettings = {
      singleSelection: false,
      idField: 'item_id',
      textField: 'item_text',
      selectAllText: 'Seleccionar Todos',
      unSelectAllText: 'Limpiar busqueda',
      itemsShowLimit: 0,
      allowSearchFilter: true,
      noDataAvailablePlaceholderText: 'No hay información disponible',
      // enableCheckAll: false
    };
    this.dropdownSettingsdocumenttypes = {
      singleSelection: true,
      idField: 'item_id',
      textField: 'item_text',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 0,
      allowSearchFilter: true,
      enableCheckAll: false,
      noDataAvailablePlaceholderText: 'No hay información disponible',
    };
    this.dropdownSettingsDepartamento = {
      singleSelection: false,
      idField: 'item_id',
      textField: 'item_text',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 0,
      allowSearchFilter: true,
      enableCheckAll: false,
      noDataAvailablePlaceholderText: 'No hay información disponible',
    };
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

  sqlAddLocation(): void {
    this.addDepartamentoAndMunicipio(this.user);
  }

  sqlconvenio(): void {
    this.updateConvenio(this.user);
  }

  sqlAddAgreements(): void {
    this.addConvenioAndPrograma(this.user);
  }

  sqlAddNewLocation(): void {
    this.addLocation();
  }

  addTime(): void {
    const a = '1';
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
    user.documentType = this.editForm.get(['documentType'])!.value.toString();
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
    let ad = this.editForm.get(['departamento'])!.value;

    //Se selecciona -TODOS-
    if (ad.indexOf('-TODOS-') >= 0) {
      ad = '-TODOS-';
      this.municipio = [];
      this.editForm.patchValue({
        municipio: [],
      });
      this.departamentoName = ad.toString();
      this.municipioName = '';
    } else {
      this.departamentoName = ad.toString();
    }

    //Se vuelve a dar "agregar departamento" despúes de dar "agregar municipio" e interactuar, hay dos opciones:
    //1. Se agrega un dep que no estaba           ->  Actualizar lista municipios disponible y mantiene los seleccionados de los deps que estaban
    //2. se cambian totalmente el departamento    ->  Actualizar lista municipios disponibles y borra los seleccionados de los dep anteriores
    if (this.departamentosListFull.length !== 0) {
      this.departamentosListFull = [];
      this.municipiosListFull = [];
      this.addLocationVerification = true;
      this.saveReady = true;
    }

    this.userService.getIdMultiplesDepartamentos(ad).subscribe(xx => {
      //MUESTRA LOS MUNICIPIOS EN EL FRONTEND
      this.addMunicipiosVerification = false;
      this.nameDepartamentos = ad;
      this.idDepartamentos = xx; //Lista tamaño n con id de departamentos
      this.userService.getMultiplesMunicipios(this.idDepartamentos).subscribe(xxx => {
        this.municipio = xxx;
        this.addMunicipiosVerification = true;

        if (this.EditOrCreate === false) {
          this.userService.findMunicipiosNameByIDAndDepartamento(this.user.id!, this.idDepartamentos).subscribe(x => {
            this.municipiosListNameFullEmpty = [];
            this.municipiosListNameFullEmpty = x;
            this.editForm.patchValue({
              municipio: this.municipiosListNameFullEmpty,
            });
          });
        }
      });
    });
  }

  private addDepartamentoAndMunicipio(user: User): void {
    const ad = this.editForm.get(['municipio'])!.value;
    this.municipiosList = ad;
    this.municipioName = ad.toString();
    this.idDepartamentos2 = [];
    this.addLocationVerification2 = false;
    if (this.municipio.length === ad.length) {
      this.municipioName = 'Todos los Municipios de ' + this.departamentoName;
    } else {
      this.municipioName = ad.toString();
    }

    if (ad.length !== 0) {
      //AQUI SE INICIA EL MODAL
      this.modalService.open(loadingModalComponent);

      for (let index = 0; index < ad.length; index++) {
        this.userService.findDepartamentosIDByMunicipioName(ad[index]).subscribe(x => {
          this.departamentosListFull.push(x);
        });

        this.userService.getIdMunicipios(ad[index]).subscribe({
          next: xx => {
            this.municipiosListId[index] = xx.toString();
            /* eslint-disable */
            /* eslint-enable */

            if (index === ad.length - 1) {
              this.verificationMunicipiosFinish = true;
              this.addLocationVerification = false;
              this.addLocationVerification2 = true;
              this.modalService.dismissAll();
              //AQUI SE TERMINA EL MODAL
            }
          },
        });
      }
      this.idMunicipiosError = false;
    } else {
      this.idMunicipiosError = true;
    }

    if (this.municipioName === '-TODOS-') {
      this.addLocationVerification = true;
    } else {
      if (this.verificationMunicipiosFinish === true) {
        this.saveReady = true;
      }
    }
  }

  private addLocation(): void {
    if (this.municipiosListFull.length === 0) {
      for (let index = 0; index < this.municipiosListId.length; index++) {
        this.municipiosListFull.push(this.municipiosListId[index]);
        if (index === this.municipiosListId.length - 1) {
          this.saveReady = false;
        }
      }
    }
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
