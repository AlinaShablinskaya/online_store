package by.it.academy.onlinestore.dao.impl;

import by.it.academy.onlinestore.dao.DBConnector;
import by.it.academy.onlinestore.dao.CrudDao;
import by.it.academy.onlinestore.dao.exception.DataBaseRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public abstract class AbstractCrudDaoImpl<E> implements CrudDao<E, Integer> {
    protected final DBConnector connector;
    private final String saveQuery;
    private final String saveAllQuery;
    private final String findByIdQuery;
    private final String findAllQueryOnPage;
    private final String findAllQuery;
    private final String updateQuery;
    private final String deleteByIdQuery;

    protected AbstractCrudDaoImpl(DBConnector connector, String saveQuery, String saveAllQuery, String findByIdQuery,
                                  String findAllQueryOnPage, String findAllQuery, String updateQuery, String deleteByIdQuery) {
        this.connector = connector;
        this.saveQuery = saveQuery;
        this.saveAllQuery = saveAllQuery;
        this.findByIdQuery = findByIdQuery;
        this.findAllQueryOnPage = findAllQueryOnPage;
        this.findAllQuery = findAllQuery;
        this.updateQuery = updateQuery;
        this.deleteByIdQuery = deleteByIdQuery;
    }

    private static final BiConsumer<PreparedStatement, Integer> INTEGER_CONSUMER =
            (PreparedStatement preparedStatement, Integer param) -> {
        try {
            preparedStatement.setInt(1, param);
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    };

    private static final BiConsumer<PreparedStatement, String> STRING_CONSUMER =
            (PreparedStatement preparedStatement, String param) -> {
        try {
            preparedStatement.setString(1, param);
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    };

    @Override
    public Optional<E> save(E entity) {
       try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(saveQuery)) {
           insert(preparedStatement, entity);
           try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                Integer newId = resultSet.getInt(1);
                return findById(newId);
           }
       } catch (SQLException e) {
           throw new DataBaseRuntimeException("Insertion is failed", e);
       }
    }

    @Override
    public void saveAll(List<E> entities) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveAllQuery)) {
            for (E entity : entities) {
                insert(preparedStatement, entity);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new DataBaseRuntimeException("Save all failed", e);
        }
    }

    @Override
    public Optional<E> findById(Integer id) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findByIdQuery)) {
            INTEGER_CONSUMER.accept(preparedStatement, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.ofNullable(mapResultSetToEntity(resultSet)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataBaseRuntimeException("Found not give result!", e);
        }
    }

    @Override
    public List<E> findAll(int page, int itemsPerPage) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findAllQueryOnPage)) {
            List<E> entities = new ArrayList<>();
            preparedStatement.setInt(1, itemsPerPage);
            preparedStatement.setInt(2, page);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    entities.add(mapResultSetToEntity(resultSet));
                }
                return entities;

            }
        } catch (SQLException e) {
            throw new DataBaseRuntimeException("Found not give result!", e);
        }
    }

    @Override
    public List<E> findAll() {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findAllQuery)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<E> entities = new ArrayList<>();
                while (resultSet.next()) {
                    entities.add(mapResultSetToEntity(resultSet));
                }
                return entities;
            }
        } catch (SQLException e) {
            throw new DataBaseRuntimeException("Found not give result!", e);
        }
    }

    @Override
    public void update(E entity) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            updateValues(preparedStatement, entity);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseRuntimeException("Update is failed!", e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteByIdQuery)) {
            INTEGER_CONSUMER.accept(preparedStatement, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseRuntimeException("Delete is failed!", e);
        }
    }

    public void saveRelation(Integer firstId, Integer secondId, String saveRelationQuery) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveRelationQuery)) {
            preparedStatement.setInt(1, firstId);
            preparedStatement.setInt(2, secondId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseRuntimeException("Relation insert failed", e);
        }
    }

    public List<String> findRelation(Integer firstId, Integer secondId, String findRelationQuery) {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(findRelationQuery)) {
            statement.setInt(1, firstId);
            statement.setInt(2, secondId);
            List<String> ids = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ids.add(resultSet.getString(1));
                    ids.add(resultSet.getString(2));
                }
                return ids;
            }
        } catch (SQLException e) {
            throw new DataBaseRuntimeException("Find relation failed", e);
        }
    }

    public void removeRelation(Integer firstId, Integer secondId, String deleteRelationQuery) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteRelationQuery)) {
            preparedStatement.setInt(1, firstId);
            preparedStatement.setInt(2, secondId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseRuntimeException("Remove relation failed", e);
        }
    }

    protected Optional<E> findByStringParameter (String parameter, String query) {
        return findByParam(parameter, query, STRING_CONSUMER);
    }

    protected Optional<E> findByIntParameter (Integer parameter, String query) {
        return findByParam(parameter, query, INTEGER_CONSUMER);
    }

    protected List<E> findAllByIntParameter(Integer parameter, String query) {
        return findAllByParameter(parameter, query, INTEGER_CONSUMER);
    }

    protected List<E> findAllByStringParam(String parameter, String query) {
        return findAllByParameter(parameter, query, STRING_CONSUMER);
    }

    private <P> Optional<E> findByParam(P param, String query,
                                        BiConsumer<PreparedStatement, P> consumer) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            consumer.accept(preparedStatement, param);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.ofNullable(mapResultSetToEntity(resultSet))
                        : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    private  <P> List<E> findAllByParameter(P parameter, String query, BiConsumer<PreparedStatement, P> consumer) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            List<E> entities = new ArrayList<>();
            consumer.accept(preparedStatement, parameter);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    entities.add(mapResultSetToEntity(resultSet));
                }
                return entities;
            }
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    protected abstract E mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    protected abstract void insert(PreparedStatement preparedStatement, E entity) throws SQLException;

    protected abstract void updateValues(PreparedStatement preparedStatement, E entity) throws SQLException;
}
