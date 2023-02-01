package com.company.inventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.dao.IProductDao;
import com.company.inventory.model.Category;
import com.company.inventory.model.Product;
import com.company.inventory.response.ProductResponseRest;
import com.company.inventory.util.Util;

@Service
public class ProductServiceImpl implements IProductService {
	
	private ICategoryDao categoryDao;
	private IProductDao productDao;
	
	public ProductServiceImpl(ICategoryDao categoryDao, IProductDao productDao) {
		super();
		this.categoryDao = categoryDao;
		this.productDao = productDao;
	}

	@Override
	@Transactional
	public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {
		
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		
		try {
			
			//search category to set in the product object
			Optional<Category> category = categoryDao.findById(categoryId);
			
			if(category.isPresent()) {
				product.setCategory(category.get());
			}else {
				response.setMetadata("respuesta nok", "-1", "Categoria no encontrada asociada al producto");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			//save the product
			Product productSaved = productDao.save(product);
			
			if (productSaved != null) {
				list.add(productSaved);
				response.getProduct().setProducts(list);
				response.setMetadata("respuesta ok", "00", "Producto guardado");
			}else {
				response.setMetadata("respuesta nok", "-1", "Producto no guardado");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST);
			}
			
		}catch(Exception e) {
			e.getStackTrace();
			response.setMetadata("respuesta nok", "-1", "Error al guardar producto");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional (readOnly = true)
	public ResponseEntity<ProductResponseRest> searchById(Long id) {
		
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		
		try {
			
			//search producto by id
			Optional<Product> product = productDao.findById(id);
			
			if( product.isPresent()) {
				
				byte[] imageDescompressed = Util.decompressZLib(product.get().getPicture());
				product.get().setPicture(imageDescompressed);
				list.add(product.get());
				response.getProduct().setProducts(list);
				response.setMetadata("Respuesta ok", "00", "Producto encontrado");
				
			} else {
				response.setMetadata("respuesta nok", "-1", "Producto no encontrada ");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("respuesta nok", "-1", "Error al guardar producto");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional (readOnly = true)
	public ResponseEntity<ProductResponseRest> searchByName(String name) {
		
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		List<Product> listAux = new ArrayList<>();
		
		try {
			
			//search producto by name
			listAux = productDao.findByNameContainingIgnoreCase(name);
			
			
			if( listAux.size() > 0) {
				
				listAux.stream().forEach( (p) -> {
					byte[] imageDescompressed = Util.decompressZLib(p.getPicture());
					p.setPicture(imageDescompressed);
					list.add(p);
				});
				
				
				response.getProduct().setProducts(list);
				response.setMetadata("Respuesta ok", "00", "Productos encontrados");
				
			} else {
				response.setMetadata("respuesta nok", "-1", "Productos no encontrados ");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("respuesta nok", "-1", "Error al buscar producto por nombre");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<ProductResponseRest> deleteById(Long id) {
		
		ProductResponseRest response = new ProductResponseRest();
		
		try {
			
			//delete producto by id
			productDao.deleteById(id);
			response.setMetadata("Respuesta ok", "00", "Producto eliminado");
			
			
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("respuesta nok", "-1", "Error al eliminar producto");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional (readOnly = true)
	public ResponseEntity<ProductResponseRest> search() {


		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		List<Product> listAux = new ArrayList<>();
		
		try {
			
			//search producto
			listAux = (List<Product>) productDao.findAll();
			
			
			if( listAux.size() > 0) {
				
				listAux.stream().forEach( (p) -> {
					byte[] imageDescompressed = Util.decompressZLib(p.getPicture());
					p.setPicture(imageDescompressed);
					list.add(p);
				});
				
				
				response.getProduct().setProducts(list);
				response.setMetadata("Respuesta ok", "00", "Productos encontrados");
				
			} else {
				response.setMetadata("respuesta nok", "-1", "Productos no encontrados ");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("respuesta nok", "-1", "Error al buscar productos");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<ProductResponseRest> update(Product product, Long categoryId, Long id) {
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		
		try {
			
			//search category to set in the product object
			Optional<Category> category = categoryDao.findById(categoryId);
			
			if( category.isPresent()) {
				product.setCategory(category.get());
			} else {
				response.setMetadata("respuesta nok", "-1", "Categoria no encontrada asociada al producto ");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			
			//search Product to update
			Optional<Product> productSearch = productDao.findById(id);
			
			if (productSearch.isPresent()) {
				
				//se actualizará el producto
				productSearch.get().setAccount(product.getAccount());
				productSearch.get().setCategory(product.getCategory());
				productSearch.get().setName(product.getName());
				productSearch.get().setPicture(product.getPicture());
				productSearch.get().setPrice(product.getPrice());
				
				//save the product in DB
				Product productToUpdate = productDao.save(productSearch.get());
				
				if (productToUpdate != null) {
					list.add(productToUpdate);
					response.getProduct().setProducts(list);
					response.setMetadata("respuesta ok", "00", "Producto actualizado");
				} else {
					response.setMetadata("respuesta nok", "-1", "Producto no actualizado ");
					return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST);

				}

			} else {
				response.setMetadata("respuesta nok", "-1", "Producto no actualizado ");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);

			}
			
			
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("respuesta nok", "-1", "Error al actualizar producto");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}

}
