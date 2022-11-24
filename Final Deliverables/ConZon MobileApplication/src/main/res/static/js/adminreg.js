const form = document.getElementById("forms");
const user = document.getElementById("mail");
const password = document.getElementById("password");
const reqid = document.getElementById("RequestID");
form.addEventListener("submit",e => {
    e.preventDefault();
    process();
})
function process()
{
    const usern = user.value.trim();
    const pass = password.value.trim();
    const requid = password.value.trim();

    u=1;p=1;r=1
    if(usern === '')
    {
        setError(user,"!PLEASE ENTER MAIL ID")
        u = 0;
    }
    if (pass === '')
    {
        setError(password,"!PLEASE ENTER PASSWORD")
        p=0;
    }
    if (requid === '')
    {
        setError(reqid,"!PLEASE ENTER REQUEST ID")
        r=0;
    }
    if(u===1 && p===1 && r == 1)
    {
        document.getElementById("forms").action = "/admin/registration";
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

