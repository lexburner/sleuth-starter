create table if not exists zipkin_spans (
  `trace_id_high` bigint not null default 0 comment 'If non zero, this means the trace uses 128 bit traceIds instead of 64 bit',
  `trace_id` bigint not null,
  `id` bigint not null,
  `name` varchar(255) not null,
  `parent_id` bigint,
  `debug` bit(1),
  `start_ts` bigint comment 'Span.timestamp(): epoch micros used for endTs query and to implement TTL',
  `duration` bigint comment 'Span.duration(): micros used for minDuration and maxDuration query'
) engine=innodb row_format=compressed character set=utf8 collate utf8_general_ci;

alter table zipkin_spans add unique key(`trace_id_high`, `trace_id`, `id`) comment 'ignore insert on duplicate';
alter table zipkin_spans add index(`trace_id_high`, `trace_id`, `id`) comment 'for joining with zipkin_annotations';
alter table zipkin_spans add index(`trace_id_high`, `trace_id`) comment 'for getTracesByIds';
alter table zipkin_spans add index(`name`) comment 'for getTraces and getSpanNames';
alter table zipkin_spans add index(`start_ts`) comment 'for getTraces ordering and range';

create table if not exists zipkin_annotations (
  `trace_id_high` bigint not null default 0 comment 'If non zero, this means the trace uses 128 bit traceIds instead of 64 bit',
  `trace_id` bigint not null comment 'coincides with zipkin_spans.trace_id',
  `span_id` bigint not null comment 'coincides with zipkin_spans.id',
  `a_key` varchar(255) not null comment 'BinaryAnnotation.key or Annotation.value if type == -1',
  `a_value` blob comment 'BinaryAnnotation.value(), which must be smaller than 64KB',
  `a_type` int not null comment 'BinaryAnnotation.type() or -1 if Annotation',
  `a_timestamp` bigint comment 'Used to implement TTL; Annotation.timestamp or zipkin_spans.timestamp',
  `endpoint_ipv4` int comment 'Null when Binary/Annotation.endpoint is null',
  `endpoint_ipv6` binary(16) comment 'Null when Binary/Annotation.endpoint is null, or no IPv6 address',
  `endpoint_port` smallint comment 'Null when Binary/Annotation.endpoint is null',
  `endpoint_service_name` varchar(255) comment 'Null when Binary/Annotation.endpoint is null'
) engine=innodb row_format=compressed character set=utf8 collate utf8_general_ci;

alter table zipkin_annotations add unique key(`trace_id_high`, `trace_id`, `span_id`, `a_key`, `a_timestamp`) comment 'Ignore insert on duplicate';
alter table zipkin_annotations add index(`trace_id_high`, `trace_id`, `span_id`) comment 'for joining with zipkin_spans';
alter table zipkin_annotations add index(`trace_id_high`, `trace_id`) comment 'for getTraces/ByIds';
alter table zipkin_annotations add index(`endpoint_service_name`) comment 'for getTraces and getServiceNames';
alter table zipkin_annotations add index(`a_type`) comment 'for getTraces';
alter table zipkin_annotations add index(`a_key`) comment 'for getTraces';
alter table zipkin_annotations add index(`trace_id`, `span_id`, `a_key`) comment 'for dependencies job';

create table if not exists zipkin_dependencies (
  `day` date not null,
  `parent` varchar(255) not null,
  `child` varchar(255) not null,
  `call_count` bigint,
  `error_count` bigint
) engine=innodb row_format=compressed character set=utf8 collate utf8_general_ci;

alter table zipkin_dependencies add unique key(`day`, `parent`, `child`);