SELECT count(PRODUCT_SOLD_ID) FROM SALES;

SELECT * FROM (
SELECT sum(s.SOLD_PRICE) as total, s.PRODUCT_SOLD_ID FROM dev.SALES s
group by s.PRODUCT_SOLD_ID) p order by p.total desc LIMIT 1;

SELECT * FROM (
SELECT count(s.PRODUCT_SOLD_ID) as total, s.PRODUCT_SOLD_ID FROM dev.SALES s
group by s.PRODUCT_SOLD_ID) p order by p.total desc LIMIT 1;