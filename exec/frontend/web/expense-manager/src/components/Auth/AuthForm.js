import { useState, useContext } from "react";
import { useHistory } from "react-router-dom";

import AuthContext from "../../store/auth-context";
import classes from "./AuthForm.module.css";

import Button from "../UI/Button/Button";
import Card from "../UI/Card/Card";
import Input from "../UI/Input/Input";

const AuthForm = () => {
  const history = useHistory();
  //   const emailInputRef = useRef();
  //   const passwordInputRef = useRef();

  const authCtx = useContext(AuthContext);

  const [isLoading, setIsLoading] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const emailChangeHandler = (event) => {
    setEmail(event.target.value);
  };

  const passwordChangeHandler = (event) => {
    setPassword(event.target.value);
  };

  const submitHandler = (event) => {
    event.preventDefault();

    // const enteredEmail = emailInputRef.current.value;
    // const enteredPassword = passwordInputRef.current.value;

    // optional: Add validation

    setIsLoading(true);

    fetch(
      `${process.env.REACT_APP_SERVER_URL}/web?email=${email}&password=${password}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      }
    )
      .then((res) => {
        setIsLoading(false);
        if (res.ok) {
          return res.json();
        } else {
          return res.json().then((data) => {
            let errorMessage = "Authentication failed!";
            // if (data && data.error && data.error.message) {
            //   errorMessage = data.error.message;
            // }

            throw new Error(errorMessage);
          });
        }
      })
      .then((data) => {
        const expirationTime = new Date(
          new Date().getTime() + +data.expiresIn * 1000
        );
        authCtx.login(data.idToken, expirationTime.toISOString());
        history.replace("/");
      })
      .catch((err) => {
        alert(err.message);
      });
  };

  return (
    <section className={classes.auth}>
      <div className={classes.logo}>
        <span className={classes.title}>?????????</span>
        <span className={classes.subtitle}>
          for <span className={classes.bold}>Administrator</span>
        </span>
      </div>
      <Card>
        <form onSubmit={submitHandler}>
          <Input
            type="email"
            id="email"
            required
            // ref={emailInputRef}
            value={email}
            onChange={emailChangeHandler}
            label="?????????"
          />
          <Input
            type="password"
            id="password"
            required
            // ref={passwordInputRef}
            value={password}
            onChange={passwordChangeHandler}
            label="????????????"
          />
          <div className={classes.actions}>
            {!isLoading && <Button>?????????</Button>}
            {isLoading && (
              <span className={classes["text-sm"]}>????????? ???...</span>
            )}
          </div>
        </form>
      </Card>
    </section>
  );
};

export default AuthForm;
