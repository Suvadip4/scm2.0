console.log("script loaded");
let currentTheme=getTheme();
//initial
changeTheme();  

function changeTheme(){

    //set to web page
    changePageTheme(currentTheme,currentTheme);

    // document.querySelector("html").classList.add(currentTheme);
    //set the listener to change theme button

    const changeThemeButton=document.querySelector("#theme_change_button");
   
    const oldTheme=currentTheme; 
    changeThemeButton.addEventListener("click",(event)=>{
        const oldTheme=currentTheme;
    if(currentTheme==="dark"){
        currentTheme="light";
    }
    else{
        currentTheme="dark";
    }
    //setTheme(currentTheme);
    //document.querySelector("html").classList.remove(oldTheme);
    //document.querySelector("html").classList.add(currentTheme);
    //changeThemeButton.querySelector("span").textContent=
    //currentTheme=="light"?"dark":"Light";
    changePageTheme(currentTheme,oldTheme);
});

}
//set theme to local storage
function setTheme(theme){
    localStorage.setItem("theme",theme)
}

//get theme from local storage
function getTheme(){
    let theme=localStorage.getItem("theme");   
   return theme?theme:"light";  
}

function changePageTheme(theme,oldTheme){
    setTheme(currentTheme);
    document.querySelector("html").classList.remove(oldTheme);
    document.querySelector("html").classList.add(theme);
    document.querySelector("#theme_change_button")
    .querySelector("span").textContent=
    theme=="light"?"Dark":"Light";
}