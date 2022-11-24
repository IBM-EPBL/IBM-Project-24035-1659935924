const form = document.getElementById("forms");
const user = document.getElementById("mail");
const password = document.getElementById("password");
form.addEventListener("submit",e => {
    e.preventDefault();
    process();
})
function process()
{
    const usern = user.value.trim();
    const pass = password.value.trim();
    u=1;p=1;
    if(usern === '')
    {
        setError(user,"!PLEASE FILL ALL THE DETAILS")
        u = 0;
    }
    if (pass === '')
    {
        setError(password,"!PLEASE FILL ALL THE DETAILS")
        p=0;
    }
    if(u===1 && p===1)
    {
        document.getElementById("forms").action = "/admin/login";
        document.getElementById("forms").submit();
    }

}
function setError(input,msg)
{

    const formControl = input.parentElement;
    const small  = formControl.querySelector("small");
    formControl.className = "user error";
    small.innerText = msg;
}

