<div class="modal fade " id="signUpModal" tabindex="-1" aria-labelledby="exampleModalCenterTitle"
     style="display: none; --bs-modal-width:40%" aria-modal="true" role="dialog">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalCenterTitle">Create account</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body ">
        <form method="post" action="home" class="form-control needs-validation" novalidate>
          <input type="hidden" name="action" value="create_account">
          <div class="row justify-content-center mb-4">
            <div class="col-12">
              <div class="form-floating mb-3">
                <input type="text" class="form-control" id="username" name="username"
                       placeholder="username" minlength="4" maxlength="20" required>
                <label for="username">User name</label>
                <div class="invalid-feedback">Please fill out this field. As least 4, max 20
                  characters!
                </div>
                <small class="text-danger" id="invalid-username"></small>
                <small class="text-success" id="valid-username"></small>
              </div>
            </div>
            <div class="col-12">
              <div class="form-floating mb-3">
                <input type="email" class="form-control" id="email" name="email"
                       placeholder="name@example.com" required>
                <label for="email">Email address</label>
                <div class="invalid-feedback">name@example.com</div>
                <small class="text-danger" id="invalid-email"></small>
                <small class="text-success" id="valid-email"></small>
              </div>
            </div>
            <div class="col-12">
              <div class="form-floating">
                <input type="password" class="form-control" minlength="6" maxlength="20" id="password"
                       name="password"
                       aria-labelledby="passwordHelpBlock" placeholder="Password" required>
                <label for="password">Password</label>
                <div class="invalid-feedback">At least 6, max 20 characters</div>
              </div>
              <div id="passwordHelpBlock" class="invalid-feedback">
                Your password must be 8-20 characters long, contain letters, numbers, special
                characters.
              </div>
            </div>

          </div>
          <div class="row">
            <div class="col-6">
              <select class="form-select mb-2" name="gender"
                      aria-label=".form-select-lg example" required>
                <option selected disabled value="">Select gender</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
              </select>
            </div>
          </div>
          <div class="col-12">
            <div class="form-check">
              <input class="form-check-input" type="checkbox" value="" id="invalidCheck" required>
              <label class="form-check-label" for="invalidCheck">
                Agree to terms and conditions
              </label>
              <div class="invalid-feedback">
                You must agree before submitting.
              </div>
            </div>
          </div>
          <div class="modal-footer d-flex justify-content-center mt-2">
            <div class="d-grid col-12 mx-auto">
              <button class="btn btn-primary btn-lg " style="background: #0060ff"
                      type="submit">Sign Up
              </button>
            </div>
            <p class="fw-normal ">Already have account?</p>
            <p>
              <a class="link-secondary link-offset-2 link-underline-opacity-10 link-underline-opacity-100-hover"
                 data-bs-target="#signInModal" href="" data-bs-toggle="modal" style="text-align: center">Sign
                In</a></p>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
<div class="modal fade" id="signInModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <form method="post" action="home" id="loginForm">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalLabel">Sign In</h1>
          <input type="hidden" name="action" value="login">
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <div class="form-floating mb-3">
            <input type="text" class="form-control" id="loginUsernameOrEmail" name="loginUsernameOrEmail"
                   placeholder="name@example.com" required>
            <label for="loginUsernameOrEmail">Username or Email</label>
          </div>
          <div class="form-floating">
            <input type="password" class="form-control" id="loginPassword" name="loginPassword"
                   placeholder="Password" required>
            <label for="loginPassword">Password</label>
          </div>
          <small class="text-danger mt-4" id="loginFeedback"></small>
        </div>
        <div class="modal-footer d-flex justify-content-center">
          <div class="d-grid col-12 mx-auto">
            <button class="btn btn-primary btn-lg " style="background: #0060ff"
                    type="submit">Sign In
            </button>
          </div>
          <a class="link-secondary link-offset-2 link-underline-opacity-10 link-underline-opacity-100-hover"
             data-bs-target="#signUpModal" href="" data-bs-toggle="modal" style="text-align: center">Create
            account</a>
        </div>
      </form>
    </div>
  </div>
</div>