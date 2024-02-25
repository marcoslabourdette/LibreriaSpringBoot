window.addEventListener('load', () => {
    const header = document.getElementById("header");
    const scrollPos = window.scrollY;

    if (scrollPos > 30) {
        header.style.backgroundColor = "#ffff";
        header.style.boxShadow="0px 0px 5px 0px grey";
    } else {
        header.style.backgroundColor = "transparent";
        header.style.boxShadow="none";
    }

    window.addEventListener('scroll', () => {
        const scrollPos = window.scrollY;
        if (scrollPos > 30) { 
            header.style.backgroundColor = "#ffff";
            header.style.boxShadow="0px 0px 5px 0px grey";
        } else {
            header.style.backgroundColor = "transparent";
            header.style.boxShadow="none";
        }
    });
    const btnMenu = document.getElementById("btnMenu");
    const navBar = document.querySelector(".navbar-menu");
    const modalOverlay = document.getElementById("modalOverlay");
    const btnCerrar = document.getElementById("cerrar");
    const modal = document.querySelector(".home");
    const btnModal = document.getElementById("btnModal");
    const btnCerrarModal = document.querySelector(".cerrar-modal");
   window.addEventListener('resize', () => {
    if (window.innerWidth > 600) { 
        modalOverlay.style.display = "none";
        btnCerrar.style.display = "none";
    } else if (navBar.classList.contains("visible")) {
        modalOverlay.style.display = "block";
        btnCerrar.style.display = "block";
    }
});
    btnMenu.addEventListener("click", () => {
            navBar.classList.add("visible");    
            modalOverlay.style.display = "block";
            btnCerrar.style.display = "block";
        
    });
    btnCerrar.addEventListener("click",()=>{
        navBar.classList.remove("visible");
        modalOverlay.style.display = "none";
    });
    btnModal.addEventListener("click",()=>{
        modalOverlay.style.transitionDelay=".6s";
        modalOverlay.style.display = "block";
        modal.classList.add("visibleModal");
      
    });
    btnCerrarModal.addEventListener("click", () => {
        modal.classList.remove("visibleModal");
        modalOverlay.style.display = "none";
    });
    const mensajeExito = document.getElementById("mensaje-exito");
    if (mensajeExito) {
        setTimeout(function() {
            mensajeExito.style.display="none";
            mensajeExito.style.opacity="0";
            mensajeExito.style.transitionDuration=".6s";
        }, 1000); 
    }
});
