const btnMenu = document.getElementById("btnMenu");
const navBar = document.querySelector(".navbar-menu");
const navBarItems = document.querySelectorAll(".navbar-menu .navbar-item");
const btnCerrar = document.getElementById("cerrar");
const btnCerrarModales = document.querySelectorAll(".cerrar-modal");
const modalOverlay = document.getElementById("modalOverlay");
const modalInfoLibro = document.querySelector(".infoLibro");
const modalInfoAutor = document.querySelector(".infoAutor");
const header = document.getElementById("header");
const modal = document.querySelector(".home"); 
const h3Modal = document.querySelector('.home-text h3');
const modalInfo = document.querySelector(".home-text");
const modalImg = document.querySelector(".home-img");
const formLibro = document.querySelector(".home .form");
const modalAutor = document.querySelector(".modalAutor");
const modalEditorial = document.querySelector(".modalEditorial");
const modalBorrar = document.querySelector(".borrarLibro");
const modalExito = document.getElementById("mensaje-exito");
const modalError = document.getElementById("mensaje-error");



function ajustarEstiloHeader() {
    const scrollPos = window.scrollY;
    if (scrollPos > 30) {
        header.style.backgroundColor = "#ffff";
        header.style.boxShadow = "0px 0px 5px 0px grey";
    } else {
        header.style.backgroundColor = "transparent";
        header.style.boxShadow = "none";
    }
}

window.addEventListener('load', () => {
    ajustarEstiloHeader();
    ocultarMensajeExito();
    ocultarMensajeError();
    window.addEventListener('scroll', () => {
        ajustarEstiloHeader();
    });

   window.addEventListener('resize', () => {
    if(modalInfoAutor.classList.contains("visibleModal") || modalInfoLibro.classList.contains("visibleModal") || modalBorrar.classList.contains("visibleModal") || modal.classList.contains("visibleModal") ||    modalEditorial.classList.contains("visibleModal") ||modalAutor.classList.contains("visibleModal")){
        abrirOverlay();
    }
    else if (window.innerWidth > 600) { 
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
    btnCerrarModales.forEach(btn=>{
        btn.addEventListener("click",()=>{
            modal.classList.remove("visibleModal");
            modalAutor.classList.remove("visibleModal");
            modalEditorial.classList.remove("visibleModal");
            cerrarOverlay();
        });
    });
    navBarItems.forEach(item=>{
        item.addEventListener("click",()=>{
            navBar.classList.remove("visible");
            cerrarOverlay();
        });
    });
    modalOverlay.addEventListener("click", () => {
        modal.classList.remove("visibleModal");
        navBar.classList.remove("visible");  
        modalAutor.classList.remove("visibleModal");
        modalEditorial.classList.remove("visibleModal");
        modalBorrar.classList.remove("visibleModal");
        modalInfoLibro.classList.remove("visibleModal");
        modalInfoAutor.classList.remove("visibleModal");
        cerrarOverlay();
    });
});


function mostrarModal(verificar) {
    if (!verificar) {
        h3Modal.textContent = "Modificar libro";
    } else {
        h3Modal.textContent = "Agregar libro";
    }
    abrirOverlay();
    modal.classList.add("visibleModal");
}

function mostrarModalModificar(elemento) {
    const isbn = elemento.getAttribute("data-isbn");
    const titulo = elemento.getAttribute("data-titulo"); ;
    const descripcion = elemento.getAttribute("data-descripcion");
    const imagen = elemento.getAttribute("data-imagen");
    const inputISBN = document.getElementById("ib");
    const inputTitulo = modal.querySelector('input[name="titulo"]');
    const inputDescripcion = modal.querySelector('input[name="descripcion"]');
    const inputImagen = modal.querySelector('input[name="imagen"]');
    
    inputTitulo.value = titulo;
    inputDescripcion.value = descripcion;
    inputImagen.value = imagen;
    
    let isbnInput = modal.querySelector('input[name="isbn"]');
    isbnInput.disabled = true;
    let formGroup = isbnInput.parentNode;
    formGroup.style.display = "none";
    modalInfo.style.height="430px";
    modalImg.style.height="430px";
    inputISBN.disabled = true;
    formLibro.setAttribute("action",`/libro/modificar/${isbn}`);
    mostrarModal(false); 
   
}
function mostrarModalAgregar() {
    limpiarImputs();
    let isbnInput = modal.querySelector('input[name="isbn"]');
    isbnInput.disabled = false;
    let formGroup = isbnInput.parentNode;
    formGroup.style.display = "block";
    modalInfo.style.height="500px";
    modalImg.style.height="500px";
    formLibro.setAttribute("action",`/libro/registrar`)
    mostrarModal(true); 
}

function mostrarModalAutor(){
    modalAutor.classList.add("visibleModal");
    abrirOverlay();
} 
function mostrarModalEditorial(){
    modalEditorial.classList.add("visibleModal");
    abrirOverlay();
} 
function limpiarImputs(){
    const inputTitulo = modal.querySelector('input[name="titulo"]');
    const inputDescripcion = modal.querySelector('input[name="descripcion"]');
    const inputImagen = modal.querySelector('input[name="imagen"]');
    inputTitulo.value = "";
    inputDescripcion.value = "";
    inputImagen.value = "";
}

function eliminarAutorEditorial(elemento){
    modalVisible = true;
    const id = elemento.getAttribute("data-id");
    const nombre = elemento.getAttribute("data-nombre");
    const tipo = elemento.getAttribute("data-tipo");
    const modalBorrar = document.querySelector(".borrarLibro");
    const btnBorrarLibro = document.querySelector(".borrarLibro .confirmar");
    const nombreLibroEliminar = document.querySelector(".borrarLibro .nombreLibro");
    
    abrirOverlay();
    if(tipo == "autor"){
        btnBorrarLibro.setAttribute("href",`/autor/eliminar/${id}`);
    }
    else{
        btnBorrarLibro.setAttribute("href",`/editorial/eliminar/${id}`);
    }
    nombreLibroEliminar.textContent = `${nombre}`; 
    modalBorrar.classList.add("visibleModal");
}
function eliminarLibro(elemento){
    const isbnLibro = elemento.getAttribute("data-isbn");
    const nombreLibro = elemento.getAttribute("data-titulo");
    const modalBorrar = document.querySelector(".borrarLibro");
    const btnBorrarLibro = document.querySelector(".borrarLibro .confirmar");
    const nombreLibroEliminar = document.querySelector(".borrarLibro .nombreLibro");
    abrirOverlay();
    btnBorrarLibro.setAttribute("href",`/libro/eliminar/${isbnLibro}`)
    nombreLibroEliminar.textContent = `${nombreLibro}`; 
    modalBorrar.classList.add("visibleModal");
}
function cerrarConfirmacion(){
    cerrarOverlay();
    modalBorrar.classList.remove("visibleModal");

}
function cerrarOverlay() {
    modalOverlay.style.display = "none";
}

function abrirOverlay(){
    modalOverlay.style.display = "block";
}

function ocultarMensajeExito() {
    setTimeout(()=>{
        modalExito.style.display = "none"; 
    },2000);   
}
function ocultarMensajeError(){
    setTimeout(()=>{
        modalError.style.display = "none";
    },2000)
}

function ordenarLibrosAlfabeticamente() {
    var listaLibros = document.querySelectorAll("#librosTable tbody tr");
    var librosArray = Array.from(listaLibros);

    librosArray.sort(function (a, b) {
        var tituloA = a.querySelector("td:nth-child(2)").textContent.trim().toLowerCase();
        var tituloB = b.querySelector("td:nth-child(2)").textContent.trim().toLowerCase();
        if (tituloA < tituloB) return -1;
        if (tituloA > tituloB) return 1;
        return 0;
    });
    var contenedorLibros = document.querySelector("#librosTable tbody");
    contenedorLibros.innerHTML = "";

    librosArray.forEach(function (libro) {
        contenedorLibros.appendChild(libro);
    });
};


function mostrarInfoAutor(elemento){
    const nombre = elemento.getAttribute("data-nombre");
    const nacionalidad = elemento.getAttribute("data-nacionalidad");
    const fechaNacimiento = elemento.getAttribute("data-nacimiento");
    const urlImagen = elemento.getAttribute("data-imagen");
    
    const banderas = {
        'argentina': '🇦🇷',
        'argentino': '🇦🇷',
        'brasilero': '🇧🇷',
        'brasilera': '🇧🇷',
        'español': '🇪🇸',
        'española': '🇪🇸',
        'británica': '🇬🇧',
        'británico': '🇬🇧',
        'britanica': '🇬🇧',
        'britanico': '🇬🇧',
        'estadounidense': '🇺🇸',
        'japonés': '🇯🇵',
        'japones': '🇯🇵',
        'japonesa': '🇯🇵',
        'frances': '🇫🇷',
        'francés': '🇫🇷',
        'francesa': '🇫🇷',
        'aleman': '🇩🇪',
        'alemán': '🇩🇪',
        'alemana': '🇩🇪',
        'chino': '🇨🇳',
        'china': '🇨🇳',
        'mexicano': '🇲🇽',
        'mexicana': '🇲🇽',
        'colombiano': '🇨🇴',
        'colombiana': '🇨🇴',
        'peruano': '🇵🇪',
        'peruana': '🇵🇪',
        'uruguayo': '🇺🇾',
        'uruguaya': '🇺🇾',
        'chileno': '🇨🇱',
        'chilena': '🇨🇱',
        'venezolano': '🇻🇪',
        'venezolana': '🇻🇪',
    };

    const bandera = banderas[nacionalidad.toLowerCase()] || ""; 
    modalInfoAutor.innerHTML = `
    <a class="cerrar-modal"><i class="bi bi-x"></i></a>
    <div class="detalle-autor">
        <div class="imagenAutor" >
        <img src="${urlImagen}" alt="imagen-autor">
        </div>
        <div class="info-autor">
        <h3>${nombre}</h3>
        <p>${nacionalidad} ${bandera}</p>
        <p>Nacimiento: ${fechaNacimiento}</p>
        </div>
    </div>
`;
const btnCerrar = modalInfoAutor.querySelector(".cerrar-modal");
btnCerrar.addEventListener("click", () => {
    cerrarOverlay();
    modalInfoAutor.classList.remove("visibleModal");
});

abrirOverlay();
modalInfoAutor.classList.add("visibleModal");
};

function mostrarInfoLibro(elemento) {
    const titulo = elemento.getAttribute("data-titulo");
    const autor = elemento.getAttribute("data-autor");
    const editorial = elemento.getAttribute("data-editorial");
    const descripcion = elemento.getAttribute("data-descripcion");
    const urlImagen = elemento.getAttribute("data-imagen");
    const fecha = elemento.getAttribute("data-fecha");
    modalInfoLibro.innerHTML = `
        <a class="cerrar-modal"><i class="bi bi-x"></i></a>
        <div class="detalle-libro">
            <div class="imagenLibro" >
            <img src="${urlImagen}" alt="portada-libro">
            </div>
            <div class="info-libro">
            <h3>${titulo}</h3>
            <p>Autor: ${autor}</p>
            <p>Editorial: ${editorial}</p>
            <p>Fecha de alta: ${fecha}</p>
            <p>Descripción:<br>${descripcion}</p>
            </div>
        </div>
    `;

    const btnCerrar = modalInfoLibro.querySelector(".cerrar-modal");
    btnCerrar.addEventListener("click", () => {
        cerrarOverlay();
        modalInfoLibro.classList.remove("visibleModal");
    });

    abrirOverlay();
    modalInfoLibro.classList.add("visibleModal");
}

