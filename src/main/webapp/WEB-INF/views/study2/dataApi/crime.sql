show tables;

create table crime(
	idx int not null auto_increment primary key,
	year int not null,							/* 발생년도 */
	police varchar(20) not null,		/* 경찰서명 */
	robbery int,										/* 강도 */
	theft int,											/* 절도 */
	murder int,											/* 살인 */
	violence int										/* 폭력 */
);

desc crime;

select *from crime order by year, police;

select year, 
	sum(robbery) as totRobberry, sum(murder) as totMurder, sum(theft) as totTheft, sum(violence) as totViolence,
	avg(robbery) as avgRobberry, avg(murder) as avgMurder, avg(theft) as avgTheft, avg(violence) as avgViolence
	from crime
	where year=2015 and police like concat('서울', '%')
	order by year, police;