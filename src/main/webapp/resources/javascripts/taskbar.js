function exportPage(){
  
    text = "<!DOCTYPE html>\n";
   
    text += document.documentElement.outerHTML;
    
    taskbar = document.getElementById("taskbar").outerHTML;
	links = document.getElementsByTagName("link");
	scripts  = document.getElementsByTagName("script");
	
	text  = text.replace(taskbar,"");//remove taskbar
	for(var i=0;i<links.length;i++){//remove all <links>
	      
	      link = links[i].outerHTML;
	      if(link.includes("taskbar")){
	         text = text.replace(link,"");
	      }
	      
	}
	for(var i=0;i<scripts.length;i++){//remove all <script>
	   
	      sc = scripts[i].outerHTML;
	      if(sc.includes("taskbar")){
	         text = text.replace(sc,"");
	      }
	      
	}
	
  
    
    /*prepare download:*/
    var element = document.createElement('a');
    element.setAttribute('href', 'data:text/html;charset=utf-8,' + encodeURIComponent(text));
    element.setAttribute('download', 'faq-exported.html');

    element.style.display = 'none';
    document.body.appendChild(element);

    element.click();

    document.body.removeChild(element);
    return false;
 
}

window.onload = function() {
  document.getElementById("exportLink").addEventListener("click", exportPage, true);
};
