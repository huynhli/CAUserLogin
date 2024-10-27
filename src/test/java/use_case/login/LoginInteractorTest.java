package use_case.login;

import data_access.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class LoginInteractorTest {
    @Test
    public void successTest() {
        LoginInputData inputData = new LoginInputData("Paul", "password");
        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // For the success test, we need to add Paul to the data access repository before we log in.
        UserFactory factory = new CommonUserFactory();
        User user = factory.create("Paul", "password");
        userRepository.save(user);

        // This creates a successPresenter that tests whether the test case is as we expect.
        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertEquals("Paul", user.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    public void successUserLoggedInTest() {
        LoginInputData inputData = new LoginInputData("Paul", "password");
        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // For the success test, we need to add Paul to the data access repository before we log in.
        UserFactory factory = new CommonUserFactory();
        User user = factory.create("Paul", "password");
        userRepository.save(user);

        // This creates a successPresenter that tests whether the test case is as we expect.
        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertEquals("Paul", userRepository.getCurrentUser());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter);

        assertNull(userRepository.getCurrentUser());
        interactor.execute(inputData);
    }

    @Test
    public void failurePasswordMismatchTest() {
        LoginInputData inputData = new LoginInputData("Paul", "wrong");
        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // For this failure test, we need to add Paul to the data access repository before we log in, and
        // the passwords should not match.
        UserFactory factory = new CommonUserFactory();
        User user = factory.create("Paul", "password");
        userRepository.save(user);

        // This creates a presenter that tests whether the test case is as we expect.
        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Incorrect password for \"Paul\".", error);
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    public void failureUserDoesNotExistTest() {
        LoginInputData inputData = new LoginInputData("Paul", "password");
        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // Add Paul to the repo so that when we check later they already exist

        // This creates a presenter that tests whether the test case is as we expect.
        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Paul: Account does not exist.", error);
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }
}

//package use_case.login;
//
//import data_access.InMemoryUserDataAccessObject;
//import entity.CommonUserFactory;
//import entity.User;
//import entity.UserFactory;
//import org.jetbrains.annotations.NotNull;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class LoginInteractorTest {
//
//    @Test
//    public void successTest() {
//        LoginInputData inputData = new LoginInputData("Paul", "password");
//        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();
//
//        // For the success test, we need to add Paul to the data access repository before we log in.
//        UserFactory factory = new CommonUserFactory();
//        User user = factory.create("Paul", "password");
//        userRepository.save(user);
//
//        // This creates a successPresenter that tests whether the test case is as we expect.
//        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
//            @Override
//            public void prepareSuccessView(LoginOutputData user) {
//                assertEquals("Paul", user.getUsername());
//            }
//
//            @Override
//            public void prepareFailView(String error) {
//                fail("Use case failure is unexpected.");
//            }
//        };
//
//        LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter);
//        interactor.execute(inputData);
//    }
//
//    @Test
//    public void successUserLoggedInTest() {
//        LoginInputData inputData = new LoginInputData("Paul", "password");
//        InMemoryUserDataAccessObject userRepository2 = new InMemoryUserDataAccessObject();
//
//        // For the success test, we need to add Paul to the data access repository before we log in.
//        UserFactory factory = new CommonUserFactory();
//        User user = factory.create("Paul", "password");
//        userRepository2.save(user);
//
//        // This creates a successPresenter that tests whether the test case is as we expect.
//        LoginInputBoundary interactor = getLoginInputBoundary(userRepository2);
//        assertNull(userRepository2.getUsername());
//
//        interactor.execute(inputData);
//    }
//
//    private static LoginInputBoundary getLoginInputBoundary(LoginUserDataAccessInterface userRepository2) {
//        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
//            @Override
//            public void prepareSuccessView(LoginOutputData user) {
//                assertEquals("Paul", userRepository2.getCurrentUsername());
//            }
//
//            @Override
//            public void prepareFailView(String error) {
//                fail("Use case failure is unexpected.");
//            }
//        };
//
//        return new LoginInteractor(userRepository2, successPresenter);
//    }
//
//    @Test
//    public void failurePasswordMismatchTest() {
//        LoginInputData inputData = new LoginInputData("Paul", "wrong");
//        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();
//
//        // For this failure test, we need to add Paul to the data access repository before we log in, and
//        // the passwords should not match.
//        UserFactory factory = new CommonUserFactory();
//        User user = factory.create("Paul", "password");
//        userRepository.save(user);
//
//        // This creates a presenter that tests whether the test case is as we expect.
//        LoginInputBoundary interactor = getInputBoundary("Incorrect password for \"Paul\".", userRepository);
//        interactor.execute(inputData);
//    }
//
//    @NotNull
//    private static LoginInputBoundary getInputBoundary(String expected, LoginUserDataAccessInterface userRepository) {
//        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
//            @Override
//            public void prepareSuccessView(LoginOutputData user) {
//                // this should never be reached since the test case should fail
//                fail("Use case success is unexpected.");
//            }
//
//            @Override
//            public void prepareFailView(String error) {
//                assertEquals(expected, error);
//            }
//        };
//
//        return new LoginInteractor(userRepository, failurePresenter);
//    }
//
//    @Test
//    public void failureUserDoesNotExistTest() {
//        LoginInputData inputData = new LoginInputData("Paul", "password");
//        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();
//
//        // Add Paul to the repo so that when we check later they already exist
//
//        // This creates a presenter that tests whether the test case is as we expect.
//        LoginInputBoundary interactor = getInputBoundary("Paul: Account does not exist.", userRepository);
//        interactor.execute(inputData);
//    }
//}