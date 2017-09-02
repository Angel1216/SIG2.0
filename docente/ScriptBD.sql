USE administration


-- Crea tabla "docentes"
create table docentes(
 idDocente int not null identity,
 nombre varchar(50) not null,
 paterno varchar(50) not null,
 materno varchar(50) not null,
 grupo varchar(50) not null,
 constraint PKDocentes primary key (nombre,paterno,materno,grupo)
)

-- consulta de tabla "docentes"
select * from docentes  -- delete docentes



-- Crea Stored Procedure "spi_altaRegistro"
create procedure spi_altaRegistro
         @nombre varchar(50),
         @paterno varchar(50),
         @materno varchar(50),
         @grupo varchar(50),
         @result int OUTPUT,
         @message varchar(1500) OUTPUT
 As
 Begin
         set nocount on
         
         -- Constantes
         Declare @exitoso bit = 0, @fallido bit = 1  
         
         BEGIN TRY
                 -- insertar registro  
                 insert into docentes(nombre,paterno,materno,grupo) values(@nombre,@paterno,@materno,@grupo)
                 
                 -- se retorna estatus con valor exitoso
                 set @result=@exitoso
         END TRY  
         BEGIN CATCH
                 -- se retorna estatus de error con su mensaje especifico
                 select @result=@fallido, @message = ERROR_MESSAGE()
         END CATCH  
           
         set nocount off
 End

 
 -- Executa SP "spi_altaRegistro"
 Declare @result int, @message varchar(1500)
         
 exec spi_altaRegistro
         @nombre='Angel11',
         @paterno='Martinez',
         @materno='Leon',
         @grupo='300A',
         @result=@result output,
         @message=@message output

 select @result As '@result', @message As '@message'
 
 
 
 -- Executa SP "spr_obtenerProfesores"
 create procedure spr_obtenerProfesores
 As
 Begin
       select concat(
                       '{','"idDocente":','"',convert(int,isnull(idDocente,0)),'",',
                       '"docente":','"',convert(varchar(150),concat(isnull(nombre,''),' ',isnull(paterno,''),' ',isnull(materno,''))),'"','}')
       from docentes

 End
 
 -- Executa SP "spr_obtenerProfesores"
 Exec spr_obtenerProfesores