package com.hafsalrahman.ssstest.data.remote;


import com.hafsalrahman.ssstest.data.DataTestUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;

import static org.mockito.Mockito.when;


/**
 * Created by hafsal on 10/27/17.
 */

public class RemoteUserServiceTest {

    private static UsersResponse usersResponse;
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    UserService userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        usersResponse = new DataTestUtil().getDummyUserResponse();
        when(userService.getUserList()).thenReturn(Single.create(new SingleOnSubscribe<UsersResponse>() {

            @Override
            public void subscribe(@NonNull SingleEmitter<UsersResponse> emitter) throws Exception {
                try {
                    emitter.onSuccess(usersResponse);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }

        }));
    }

    @Test
    public void testRemoteApiCall() {
        userService.getUserList().test().assertNoErrors().assertValue(usersResponse);
    }

}