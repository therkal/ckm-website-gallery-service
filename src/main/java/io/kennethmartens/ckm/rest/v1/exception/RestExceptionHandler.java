package io.kennethmartens.ckm.rest.v1.exception;

import io.kennethmartens.ckm.rest.v1.RestExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import javax.persistence.EntityExistsException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.time.Instant;

@Slf4j
public class RestExceptionHandler {

    @ServerExceptionMapper
    public RestResponse<RestExceptionResponse> notFoundExceptionHandling(NotFoundException exception) {
        log.error("Not Found Exception: %1$s", exception);
        return RestResponse.status(RestResponse.Status.NOT_FOUND, buildException(
                RestResponse.Status.NOT_FOUND,
                RestResponse.StatusCode.NOT_FOUND,
                exception.getMessage()
            )
        );
    }

    @ServerExceptionMapper
    public RestResponse<io.kennethmartens.ckm.rest.v1.exception.RestExceptionResponse> badRequestException(BadRequestException exception) {
        log.error("Bad Request Exception : %1$s", exception);
        return RestResponse.status(RestResponse.Status.BAD_REQUEST, buildException(
                        RestResponse.Status.BAD_REQUEST,
                        RestResponse.StatusCode.BAD_REQUEST,
                        exception.getMessage()
                )
        );
    }

    @ServerExceptionMapper
    public RestResponse<io.kennethmartens.ckm.rest.v1.exception.RestExceptionResponse> entityExistsException(EntityExistsException exception) {
        log.error("Bad Request Exception : %1$s", exception);
        return RestResponse.status(RestResponse.Status.CONFLICT, buildException(
                        RestResponse.Status.CONFLICT,
                        RestResponse.StatusCode.CONFLICT,
                        exception.getMessage()
                )
        );
    }

    private io.kennethmartens.ckm.rest.v1.exception.RestExceptionResponse buildException(RestResponse.Status status, Integer statusCode, String message) {
        return io.kennethmartens.ckm.rest.v1.exception.RestExceptionResponse.builder()
                .status(status)
                .statusCode(statusCode)
                .timestamp(Instant.now())
                .message(message)
                .build();
    }

}