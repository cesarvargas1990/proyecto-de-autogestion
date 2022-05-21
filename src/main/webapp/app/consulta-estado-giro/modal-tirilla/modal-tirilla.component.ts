import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { TransaccionesNominaService } from 'app/entities/transacciones-nomina/service/transacciones-nomina.service';

@Component({
  selector: 'jhi-tirilla-modal',
  templateUrl: './modal-tirilla.component.html',
})
export class TirillaModalComponent implements OnInit {
  pdfLink = '';

  constructor(
    private activeModal: NgbActiveModal,
    private sanitizer: DomSanitizer,
    protected transaccionesNominaService: TransaccionesNominaService
  ) {}
  ngOnInit(): void {
    console.log('algo');
    this.transaccionesNominaService.getPDF().subscribe({
      next: algo => {
        console.log(algo);
      },
      error: algoerror => {
        console.log(algoerror);
        this.pdfLink = algoerror.error.text;
      },
    });
  }

  public clearUrl(url: string) {
    // return this.sanitizer.bypassSecurityTrustUrl(window.location.protocol + '//' + window.location.host + url);
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }
  dismiss(): void {
    this.activeModal.dismiss();
  }
}
