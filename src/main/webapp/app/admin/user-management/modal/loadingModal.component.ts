import { Component, ViewChild, OnInit, AfterViewInit, ElementRef } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from 'app/login/login.service';
import { AccountService } from 'app/core/auth/account.service';
import { ModalDismissReasons, NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-loading-modal',
  templateUrl: './loadingModal.component.html',
  providers: [NgbModalConfig, NgbModal],
})
export class loadingModalComponent implements OnInit, AfterViewInit {
  closeResult?: string;
  @ViewChild('content') myModal: string | undefined;

  constructor(private modalService: NgbModal, config: NgbModalConfig) {
    config.backdrop = 'static';
    config.keyboard = false;
    config.centered = true;
  }

  ngAfterViewInit(): void {
    this.open(this.myModal);
  }

  dismiss(): void {
    this.modalService.dismissAll();
  }

  open(content: string | undefined): void {
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(result => {
      //this.closeResult = `Closed with: ${result}`;
    });
  }

  ngOnInit(): void {
    //throw new Error('Method not implemented.');
    const a = 1;
  }
}
