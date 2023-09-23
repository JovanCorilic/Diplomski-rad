import { Component, EventEmitter, Input, OnChanges, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent implements OnInit, OnChanges{
  @Input() totalItems: number | undefined;
	@Input() pageSize: number | undefined;
	@Output() pageSelected: EventEmitter<number>;
	pages: number[] | undefined;
	activePage: number;

  constructor(
		
	) {
		this.pageSelected = new EventEmitter();
		this.activePage = 1;
	}

  ngOnInit() {
    this.pages = [];
    if(this.totalItems && this.pageSize){
      for (let i = 1; i <= this.getNoPages(this.totalItems, this.pageSize); i++) {
        this.pages.push(i);
      }
    }
	}

	ngOnChanges(changes: any) {
		this.totalItems = changes.totalItems.currentValue;
    this.pages = [];
    if(this.totalItems && this.pageSize){
      for (let i = 1; i <= this.getNoPages(this.totalItems, this.pageSize); i++) {
        this.pages.push(i);
      }
    }
	}

	selected(newPage: number) {
    if(this.totalItems && this.pageSize){
      if (newPage >= 1 && newPage <= this.getNoPages(this.totalItems, this.pageSize)) {
        this.activePage = newPage;
        this.pageSelected.emit(this.activePage);
      }
    }
	}

  public getNoPages(totalItems: number, pageSize: number): number {
		/*console.log("samo");
		console.log(totalItems);
		console.log(pageSize);
		console.log(Math.ceil(totalItems / pageSize));*/
		return Math.ceil(totalItems / pageSize);
	}

}
