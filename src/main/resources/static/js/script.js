const btnMenu = document.getElementById("btnMenu");
const navBar = document.querySelector(".navbar-menu");
const btnCerrar = document.getElementById("cerrar");
const btnCerrarModal = document.querySelector(".cerrar-modal");
const modalOverlay = document.getElementById("modalOverlay");
const modalInfoLibro = document.querySelector(".infoLibro");
const header = document.getElementById("header");
const modal = document.querySelector(".home"); 
const h3Modal = document.querySelector('.home-text h3');
const modalInfo = document.querySelector(".home-text");
const modalImg = document.querySelector(".home-img");
const formLibro = document.querySelector(".form");

window.addEventListener('load', () => {
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

   window.addEventListener('resize', () => {
    if( modal.classList.contains("visibleModal")){
        abrirOverlay();
    }
    else if (!modalInfoLibroAbierto && window.innerWidth > 600) { 
        cerrarOverlay();
        btnCerrar.style.display = "none";
    } else if (navBar.classList.contains("visible")) {
        abrirOverlay();
        btnCerrar.style.display = "block";
    }
});
    btnMenu.addEventListener("click", () => {
            navBar.classList.add("visible");    
            abrirOverlay();
            btnCerrar.style.display = "block";
        
    });
    btnCerrar.addEventListener("click",()=>{
        navBar.classList.remove("visible");
        cerrarOverlay();
    });
   
    btnCerrarModal.addEventListener("click", () => {
        modal.classList.remove("visibleModal");
        cerrarOverlay();
    });
    const mensajeExito = document.getElementById("mensaje-exito");
    if (mensajeExito) {
        setTimeout(function() {
            mensajeExito.style.display="block";
        }, 1000); 
    }; 
});


function mostrarModal(verificar) {
    if (!verificar) {
        h3Modal.textContent = "Modificar libro";
    } else {
        h3Modal.textContent = "Agregar libro";
    }
    modalOverlay.style.transitionDelay = ".6s";
    abrirOverlay();
    modal.classList.add("visibleModal");
}

function mostrarModalModificar(elemento) {
    const isbn = elemento.getAttribute("data-isbn");
    const inputISBN = document.getElementById("ib");
    const formGroupISBN = document.querySelector(".form-group");
    modalInfo.style.height="430px";
    modalImg.style.height="430px";
    inputISBN.disabled = true;
    formGroupISBN.style.display = "none";
    formLibro.setAttribute("action",`/libro/modificar/${isbn}`);
    mostrarModal(false); 
   
}
function mostrarModalAgregar() {
    const formGroupISBN = document.querySelector(".form-group");
    const inputISBN = document.getElementById("ib");
    modalInfo.style.height="500px";
    modalImg.style.height="500px";
    inputISBN.disabled = false;
    formGroupISBN.style.display = "block";
    formLibro.setAttribute("action",`/libro/registrar`)
    mostrarModal(true); 
}

function cerrarOverlay() {
    modalOverlay.style.display = "none";
}
//Funcion para abrir overlay.
function abrirOverlay(){
    modalOverlay.style.display = "block";
}

let modalInfoLibroAbierto = false;
function mostrarInfoLibro(elemento) {
    const titulo = elemento.getAttribute("data-titulo");
    const autor = elemento.getAttribute("data-autor");
    const editorial = elemento.getAttribute("data-editorial");
    const isbn = elemento.getAttribute("data-isbn");
    const descripcion = elemento.getAttribute("data-descripcion");
    
    modalInfoLibroAbierto = true;
    
    modalInfoLibro.innerHTML = `
        <a class="cerrar-modal"><i class="bi bi-x"></i></a>
        <div>
            <h3>${titulo}</h3>
            <p>ISBN: ${isbn}</p>
            <p>Autor: ${autor}</p>
            <p>Editorial: ${editorial}</p>
            <p>Descripción: ${descripcion}</p>
        </div>
    `;

    const btnCerrar = modalInfoLibro.querySelector(".cerrar-modal");
    btnCerrar.addEventListener("click", () => {
        modalOverlay.style.display = 'none';
        modalInfoLibro.style.display="none";
        modalInfoLibroAbierto = false;
    });

    modalOverlay.style.display = 'block';
    modalInfoLibro.style.display = "flex";
}

