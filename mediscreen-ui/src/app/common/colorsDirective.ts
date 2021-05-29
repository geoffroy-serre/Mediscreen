import {Directive, ElementRef, Input, OnInit, Renderer2} from "@angular/core";

@Directive({
  selector: '[colors]'
})

export class ColorsDirective implements OnInit {
  @Input() colors: string = "";
  constructor(private renderer: Renderer2, private el: ElementRef) {}

  ngOnInit() {
    const current = this._getStyles(this.colors);
    this.renderer.setStyle(
      this.el.nativeElement,
      'color',
      current
    );
  }
  _getStyles(key: string) {
    console.log("COLOOOOR: "+key);
    let color = '';
    if (key.toLowerCase()=="none") {
      color = 'green';
    }
    else if (key.toLowerCase()=="borderline") {
      color = 'yellow';
    }
    else if (key.toLowerCase()=="in danger") {
      color = 'orange';
    }
    else if (key.toLowerCase()=="early onset") {
      color = 'red';
    }
    else{
      color='blue';
    }
    return  color;
  }
}
